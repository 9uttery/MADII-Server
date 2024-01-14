package com.guttery.madii.domain.user.domain.service;

import com.guttery.madii.common.decoder.OidcJwtDecoder;
import com.guttery.madii.common.exception.CustomException;
import com.guttery.madii.common.exception.ErrorDetails;
import com.guttery.madii.domain.user.application.dto.OidcDecodePayload;
import com.guttery.madii.domain.user.application.dto.OidcPublicKeyListResponse;
import com.guttery.madii.domain.user.application.dto.OidcPublicKeyResponse;
import com.guttery.madii.domain.user.infrastructure.AppleOidcKeyClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Service
public class AppleIdTokenDecodeService {
    private final AppleOidcKeyClient appleOidcKeyClient;
    @Value("${oauth.apple.iss}")
    private String iss;
    @Value("${oauth.apple.client-id}")
    private String clientId;

    @Transactional(propagation = Propagation.MANDATORY) // Redis에 접근하므로 Tranasactional
    public OidcDecodePayload getPayloadFromIdToken(final String token) {
        final String kid = getKidFromUnsignedIdToken(token);
        final OidcPublicKeyListResponse applePublicKeyList = appleOidcKeyClient.getAppleOidcOpenKeys();

        final OidcPublicKeyResponse oidcPublicKey =
                applePublicKeyList.keys().stream()
                        .filter(o -> o.kid().equals(kid))
                        .findFirst()
                        .orElseThrow(() -> CustomException.of(ErrorDetails.APPLE_KEY_SERVER_ERROR));

        // 캐시 무효화 로직 관련해서 고민해보기.

        return OidcJwtDecoder.getOidcTokenBody(token, oidcPublicKey.n(), oidcPublicKey.e());
    }

    private String getKidFromUnsignedIdToken(final String token) {
        return OidcJwtDecoder.getKidFromUnsignedTokenHeader(token, iss, clientId);
    }
}
