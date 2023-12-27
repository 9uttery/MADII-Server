package com.guttery.madii.common.jwt;

import com.guttery.madii.common.exception.CustomException;
import com.guttery.madii.common.exception.ErrorDetails;
import com.guttery.madii.common.serializer.UserPrincipalSerializer;
import com.guttery.madii.domain.user.domain.model.Role;
import com.guttery.madii.domain.user.domain.model.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {
    public static final long ACCESS_TOKEN_PERIOD = 1000 * 60 * 60 * 24L; // 1일
    public static final long REFRESH_TOKEN_PERIOD = 1000 * 60 * 60 * 24 * 14L; // 14일
    private static final String ROLE = "role";
    private final RedisTemplate<String, String> redisTemplate;
    private final UserPrincipalSerializer userPrincipalSerializer;
    @Value("${jwt.secret}")
    private String secret;

    private Key getSecretKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    private JwtBuilder buildToken(final Date now) {
        return Jwts.builder()
                .setIssuedAt(now)
                .signWith(getSecretKey());
    }

    public String generateAccessToken(final Long id, final Role role) {
        final Date now = new Date();

        return buildToken(now)
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_PERIOD))
                .setSubject(String.valueOf(id))
                .claim(ROLE, role.toString())
                .compact();
    }

    public String generateRefreshToken(final Long id, final Role role) {
        final Date now = new Date();
        final String uuid = UUID.randomUUID().toString();
        final String generatedRefreshToken = buildToken(now)
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_PERIOD))
                .setId(uuid) // 리프레시 토큰의 무작위성을 보장
                // REDIS에 key 값으로 토큰을 넣고 value 값으로 id와 role을 넣어서 찾기
                .compact();

        final UserPrincipal userPrincipal = new UserPrincipal(id, role);

        redisTemplate.opsForValue().set(generatedRefreshToken, userPrincipalSerializer.serialize(userPrincipal), REFRESH_TOKEN_PERIOD, TimeUnit.MILLISECONDS);

        return generatedRefreshToken;
    }

    public void validateToken(final String token) {
        final JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build();

        try {
            parser.parse(token);
        } catch (final MalformedJwtException | SignatureException | IllegalArgumentException e) {
            throw CustomException.of(ErrorDetails.INVALID_TOKEN);
        } catch (final ExpiredJwtException e) {
            throw CustomException.of(ErrorDetails.EXPIRED_TOKEN);
        }
    }

    public void validateRefreshToken(final String refreshToken) {
        final JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build();

        try {
            parser.parse(refreshToken);
        } catch (final MalformedJwtException | SignatureException | IllegalArgumentException e) {
            throw CustomException.of(ErrorDetails.INVALID_REFRESH_TOKEN);
        } catch (final ExpiredJwtException e) {
            throw CustomException.of(ErrorDetails.EXPIRED_REFRESH_TOKEN);
        }
    }

    public UserPrincipal extractUserPrincipalFromToken(final String token) {
        final Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        final Long id = Long.parseLong(claims.getSubject());
        final String role = claims.get(ROLE, String.class);

        return new UserPrincipal(id, Role.valueOf(role));
    }

    public String reIssueAccessToken(final String refreshToken) {
        validateRefreshToken(refreshToken);
        final UserPrincipal userPrincipal = getUserPrincipalFromRedis(refreshToken);

        return generateAccessToken(userPrincipal.id(), userPrincipal.role());
    }

    public String reIssueRefreshToken(final String refreshToken) {
        validateRefreshToken(refreshToken);
        final UserPrincipal userPrincipal = getUserPrincipalFromRedis(refreshToken);
        redisTemplate.delete(refreshToken);

        return generateRefreshToken(userPrincipal.id(), userPrincipal.role());
    }

    private UserPrincipal getUserPrincipalFromRedis(final String refreshToken) {
        final String authenticationInfo = Optional.ofNullable(redisTemplate.opsForValue().get(refreshToken)).orElseThrow(
                () -> CustomException.of(ErrorDetails.INVALID_REFRESH_TOKEN) // 리프레시 토큰이 저장소에 존재하지 않을 경우 Error 리턴
        );

        return userPrincipalSerializer.deserialize(authenticationInfo);
    }
}
