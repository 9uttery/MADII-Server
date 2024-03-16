package com.guttery.madii.domain.user.domain.service;

import com.guttery.madii.common.decoder.OidcJwtDecoder;
import com.guttery.madii.common.exception.CustomException;
import com.guttery.madii.common.exception.ErrorDetails;
import com.guttery.madii.domain.user.application.dto.OidcDecodePayload;
import com.guttery.madii.domain.user.application.dto.OidcPublicKeyListResponse;
import com.guttery.madii.domain.user.application.dto.OidcPublicKeyResponse;
import com.guttery.madii.domain.user.infrastructure.KakaoOidcKeyClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class KakaoIdTokenDecodeService {
    private final KakaoOidcKeyClient kakaoOidcKeyClient;
    private final String iss;
    private final String clientId;

    public KakaoIdTokenDecodeService(
            KakaoOidcKeyClient kakaoOidcKeyClient,
            @Value("${oauth.kakao.iss}") String iss,
            @Value("${oauth.kakao.client-id}") String clientId
    ) {
        this.kakaoOidcKeyClient = kakaoOidcKeyClient;
        this.iss = iss;
        this.clientId = clientId;
    }

    @Transactional(propagation = Propagation.MANDATORY) // Redis에 접근하므로 Tranasactional
    public OidcDecodePayload getPayloadFromIdToken(final String token) {
        final String kid = getKidFromUnsignedIdToken(token);
        final OidcPublicKeyListResponse kakaoPublicKeyList = kakaoOidcKeyClient.getKakaoOidcOpenKeys();

        final OidcPublicKeyResponse oidcPublicKey =
                kakaoPublicKeyList.keys().stream()
                        .filter(o -> o.kid().equals(kid))
                        .findFirst()
                        .orElseThrow(() -> CustomException.of(ErrorDetails.KAKAO_KEY_SERVER_ERROR));

        return OidcJwtDecoder.getOidcTokenBody(token, oidcPublicKey.n(), oidcPublicKey.e());
    }

    private String getKidFromUnsignedIdToken(final String token) {
        return OidcJwtDecoder.getKidFromUnsignedTokenHeader(token, iss, clientId);
    }
}
