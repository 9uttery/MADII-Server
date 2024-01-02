package com.guttery.madii.domain.user.domain.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor
@Embeddable
public class UserProfile implements Serializable {
    private String nickname;
    private String image;

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (UserProfile) obj;
        return Objects.equals(this.nickname, that.nickname) &&
                Objects.equals(this.image, that.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname, image);
    }
}
