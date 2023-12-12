package com.guttery.madii.domain.user.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public record LoginInfo(
        @Column(unique = true)
        String loginId,
        String password
) implements Serializable {

}
