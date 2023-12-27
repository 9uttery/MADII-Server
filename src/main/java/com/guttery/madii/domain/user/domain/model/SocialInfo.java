package com.guttery.madii.domain.user.domain.model;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Embeddable
public class SocialInfo {
    private String socialId;
    private SocialProvider provider;

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (SocialInfo) obj;
        return Objects.equals(this.socialId, that.socialId) &&
                Objects.equals(this.provider, that.provider);
    }

    @Override
    public int hashCode() {
        return Objects.hash(socialId, provider);
    }
}
