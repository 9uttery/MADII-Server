package com.guttery.madii.domain.album.domain.model;

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
public class AlbumStatus {
    private Boolean isPersonal; // 공개 여부
    private Boolean isBlocked; // 신고 정지 여부
    private Boolean isDeleted; // 삭제 여부

    @Override
    public int hashCode() { return Objects.hash(isPersonal, isBlocked, isDeleted); }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (AlbumStatus) obj;
        return Objects.equals(this.isPersonal, that.isPersonal) &&
                Objects.equals(this.isBlocked, that.isBlocked) &&
                Objects.equals(this.isBlocked, that.isBlocked);
    }

    public void setPersonal(Boolean personal) {
        isPersonal = personal;
    }
}
