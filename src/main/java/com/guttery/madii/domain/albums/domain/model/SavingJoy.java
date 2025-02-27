package com.guttery.madii.domain.albums.domain.model;

import com.guttery.madii.common.domain.model.BaseTimeEntity;
import com.guttery.madii.domain.joy.domain.model.Joy;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
    private Integer joyOrder; // 소확행 정렬 순서

    public SavingJoy(Joy joy, Album album, Integer joyOrder) {
        this.joy = joy;
        this.album = album;
        this.joyOrder = joyOrder;
    }

    public static SavingJoy createSavingJoy(Joy joy, Album album, Integer joyOrder) {
        return new SavingJoy(joy, album, joyOrder);
    }

    public void modifyJoyOrder(Integer joyOrder) {
        this.joyOrder = joyOrder;
    }
}
