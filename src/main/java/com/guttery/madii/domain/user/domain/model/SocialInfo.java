package com.guttery.madii.domain.user.domain.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public record SocialInfo(
        String socialId,
        SocialProvider provider
) implements Serializable {
}