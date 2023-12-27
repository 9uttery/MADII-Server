package com.guttery.madii.domain.user.application.dto;

public record OidcDecodePayload(
        String iss,
        String aud,
        String sub,
        String email
) {
}
