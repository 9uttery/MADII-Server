package com.guttery.madii.common.jwt;

import com.guttery.madii.common.exception.CustomException;
import com.guttery.madii.common.exception.ErrorDetails;
import com.guttery.madii.domain.user.domain.model.UserPrincipal;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

import static com.guttery.madii.common.config.security.SecurityConstant.AUTHORIZATION_HEADER;
import static com.guttery.madii.common.config.security.SecurityConstant.AUTH_WHITELIST;
import static com.guttery.madii.common.config.security.SecurityConstant.BEARER_PREFIX;


@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String token = extractTokenFromHeader(request.getHeader(AUTHORIZATION_HEADER));
        jwtProvider.validateToken(token);
        setAuthentication(token);

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        final AntPathMatcher antPathMatcher = new AntPathMatcher();

        return AUTH_WHITELIST.stream().anyMatch(whitelist -> antPathMatcher.match(whitelist, request.getRequestURI()));
    }

    private String extractTokenFromHeader(final String header) {
        return Optional.ofNullable(header)
                .filter(headerValue -> headerValue.startsWith(BEARER_PREFIX))
                .map(headerValue -> headerValue.replace(BEARER_PREFIX, ""))
                .orElseThrow(() -> CustomException.of(ErrorDetails.INVALID_TOKEN));
    }

    private void setAuthentication(final String accessToken) {
        final UserPrincipal userPrincipal = jwtProvider.extractUserPrincipalFromToken(accessToken);
        final JwtAuthenticationToken jwtAuthenticationToken = JwtAuthenticationToken.of(userPrincipal);
        SecurityContextHolder.getContext().setAuthentication(jwtAuthenticationToken);
    }
}
