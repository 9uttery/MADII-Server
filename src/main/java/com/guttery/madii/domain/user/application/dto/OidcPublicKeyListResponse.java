package com.guttery.madii.domain.user.application.dto;

import java.util.List;

public record OidcPublicKeyListResponse(
        List<OidcPublicKeyResponse> keys
) {
}
