package com.guttery.madii.domain.user.domain;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public record LoginInfo(
        String loginId,
        String password
) implements Serializable {

}
