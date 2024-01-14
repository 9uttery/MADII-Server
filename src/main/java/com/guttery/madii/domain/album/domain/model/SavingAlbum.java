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
@Table(name = "t_saving_album")
@Access(AccessType.FIELD)
public class SavingAlbum extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long savingAlbumId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 사용자 id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id", nullable = false)
    private Album album; // 앨범 id

    public SavingAlbum(User user, Album album) {
        this.user = user;
        this.album = album;
    }

    public static SavingAlbum createSavingAlbum(User user, Album album) { return new SavingAlbum(user, album); }
}
