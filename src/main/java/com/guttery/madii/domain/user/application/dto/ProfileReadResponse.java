package com.guttery.madii.domain.user.application.dto;

public record ProfileReadResponse(
        Long id,
        String nickname,
        String image
) {
}
