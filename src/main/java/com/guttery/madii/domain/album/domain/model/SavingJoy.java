package com.guttery.madii.domain.album.domain.model;

import com.guttery.madii.common.domain.model.BaseTimeEntity;
import com.guttery.madii.domain.joy.domain.model.Joy;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "t_saving_joy")
@Access(AccessType.FIELD)
public class SavingJoy extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long savingJoyId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "joy_id", nullable = false)
    private Joy joy; // 소확행 id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id", nullable = false)
    private Album album; // 앨범 id

    public SavingJoy(Joy joy, Album album) {
        this.joy = joy;
        this.album = album;
    }

    public static SavingJoy createSavingJoy(Joy joy, Album album) { return new SavingJoy(joy, album); }
}
