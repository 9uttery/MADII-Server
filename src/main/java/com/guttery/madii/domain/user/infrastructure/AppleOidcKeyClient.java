package com.guttery.madii.domain.user.infrastructure;

import com.guttery.madii.domain.user.application.dto.OidcPublicKeyListResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        name = "apple-oidc-key-client"
)
public interface AppleOidcKeyClient {
    @Cacheable(cacheNames = "AppleOidcKey", cacheManager = "oidcCacheManager")
    @GetMapping("/auth/keys")
    OidcPublicKeyListResponse getAppleOidcOpenKeys();
}
