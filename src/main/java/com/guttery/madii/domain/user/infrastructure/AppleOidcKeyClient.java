package com.guttery.madii.domain.user.infrastructure;

import com.guttery.madii.domain.user.application.dto.OidcPublicKeyListResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        name = "AppleOidcKeyClient",
        url = "https://appleid.apple.com/auth"
)
public interface AppleOidcKeyClient {
    @Cacheable(cacheNames = "AppleOidcKey", cacheManager = "appleOidcCacheManager")
    @GetMapping("/keys")
    OidcPublicKeyListResponse getAppleOidcOpenKeys();
}
