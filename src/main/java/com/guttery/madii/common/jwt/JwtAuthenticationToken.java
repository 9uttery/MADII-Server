package com.guttery.madii.common.jwt;

import com.guttery.madii.domain.user.domain.model.UserPrincipal;
import lombok.Builder;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private final UserPrincipal userPrincipal;

    @Builder
    public JwtAuthenticationToken(final Collection<? extends GrantedAuthority> authorities, final UserPrincipal userPrincipal) {
        super(authorities);
        this.userPrincipal = userPrincipal;
        setAuthenticated(true);
    }

    public static JwtAuthenticationToken of(final UserPrincipal userPrincipal) {
        return JwtAuthenticationToken.builder()
                .userPrincipal(userPrincipal)
                .authorities(Collections.singleton(userPrincipal.role()))
                .build();
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.userPrincipal;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
