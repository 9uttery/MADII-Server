package com.guttery.madii.domain.user.infrastructure;

import com.guttery.madii.domain.user.application.dto.OidcPublicKeyListResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        name = "kakao-oidc-key-client"
)
public interface KakaoOidcKeyClient {
    @Cacheable(cacheNames = "KakaoOidcKey", cacheManager = "oidcCacheManager")
    @GetMapping("/.well-known/jwks.json")
    OidcPublicKeyListResponse getKakaoOidcOpenKeys();
}
