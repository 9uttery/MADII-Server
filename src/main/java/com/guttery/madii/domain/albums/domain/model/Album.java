package com.guttery.madii.domain.albums.domain.model;

import com.guttery.madii.common.domain.model.BaseTimeEntity;
import com.guttery.madii.domain.user.domain.model.User;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

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
    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SavingAlbum> savingAlbums;
    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SavingJoy> savingJoys;
    private LocalDateTime officialSetAt;

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

    public void modifyNameAndDes(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void makeOfficial() {
        this.albumStatus = this.albumStatus.withOfficial(true);
        this.officialSetAt = LocalDateTime.now();
    }

    public void makeBlocked() {
        this.albumStatus = this.albumStatus.withBlocked(true);
    }

}
