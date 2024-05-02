package com.guttery.madii.domain.joy.domain.model;

import com.guttery.madii.common.domain.model.BaseTimeEntity;
import com.guttery.madii.domain.albums.domain.model.SavingJoy;
import com.guttery.madii.domain.user.domain.model.User;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "t_joy")
@Access(AccessType.FIELD)
public class Joy extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long joyId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private User user;
    @Enumerated(EnumType.STRING)
    private JoyType joyType; // 소확행 타입 (공식, 개인)
    private Integer joyIconNum; // 소확행 썸네일 아이콘 번호
    @Column(length = 30)
    private String contents; // 소확행 내용
    @OneToMany(mappedBy = "joy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SavingJoy> savingJoys;

    public Joy(User user, JoyType joyType, Integer joyIconNum, String contents) {
        this.user = user;
        this.joyType = joyType;
        this.joyIconNum = joyIconNum;
        this.contents = contents;
    }

    public static Joy createPersonalJoy(User user, Integer joyIconNum, String contents) {
        return new Joy(user, JoyType.PERSONAL, joyIconNum, contents);
    }

    public void modifyContents(String contents) {
        this.contents = contents;
    }

    public void makeOfficial() {
        this.joyType = JoyType.OFFICIAL;
    }
}
