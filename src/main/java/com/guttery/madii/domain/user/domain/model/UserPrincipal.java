package com.guttery.madii.domain.user.domain.model;

import java.io.Serializable;

public record UserPrincipal(
        Long id,
        Role role
) implements Serializable {

}
