package com.guttery.madii.domain.album.domain.model;

import com.guttery.madii.common.domain.model.BaseTimeEntity;
import com.guttery.madii.domain.user.domain.model.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "t_album")
@Access(AccessType.FIELD)
public class Album extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long albumId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private User user;
    @Column(length = 30)
    private String name; // 앨범 제목
    @Column(length = 50)
    private String description; // 앨범 상세 설명
    @Embedded
    private AlbumStatus albumStatus; // 공개 여부, 신고 정지 여부, 삭제 여부
    @Embedded
    private AlbumInfo albumInfo; // 앨범 썸네일 아이콘 번호, 배경 번호

    public Album(User user, String name, String description, AlbumStatus albumStatus, AlbumInfo albumInfo) {
        this.user = user;
        this.name = name;
        this.description = description;
        this.albumStatus = albumStatus;
        this.albumInfo = albumInfo;
    }

    public static Album createAlbum(User user, String name, String description,
                                    Boolean isOfficial, Boolean isBlocked, Boolean isDeleted,
                                    Integer albumIconNum, Integer albumColorNum) {
        return new Album(user, name, description, new AlbumStatus(isOfficial, isBlocked, isDeleted), new AlbumInfo(albumIconNum, albumColorNum));
    }

    public void modifyInfo(AlbumInfo albumInfo) { this.albumInfo = albumInfo; }

    public void makeOfficial() {
        this.albumStatus = this.albumStatus.withOfficial(true);
    }

    public void makePersonal() {
        this.albumStatus = this.albumStatus.withOfficial(false);
    }

}
