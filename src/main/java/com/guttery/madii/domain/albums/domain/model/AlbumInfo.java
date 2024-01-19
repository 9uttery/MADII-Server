package com.guttery.madii.domain.albums.domain.model;

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
public final class AlbumInfo {
    private Integer albumIconNum; // 앨범 썸네일 아이콘 번호
    private Integer albumColorNum; // 앨범 썸네일 배경 번호

    @Override
    public int hashCode() { return Objects.hash(albumIconNum, albumColorNum); }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (AlbumInfo) obj;
        return Objects.equals(this.albumIconNum, that.albumIconNum) &&
                Objects.equals(this.albumColorNum, that.albumColorNum);
    }
}
