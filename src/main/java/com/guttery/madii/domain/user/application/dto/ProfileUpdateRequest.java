package com.guttery.madii.domain.user.application.dto;

public record ProfileUpdateRequest(
        String nickname,
        String image
) {
}
