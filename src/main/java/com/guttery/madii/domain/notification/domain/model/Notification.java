package com.guttery.madii.domain.notification.domain.model;

import com.guttery.madii.common.domain.model.BaseTimeEntity;
import com.guttery.madii.domain.user.domain.model.User;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "t_notification")
@Access(AccessType.FIELD)
public class Notification extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    private String title;
    private String contents;
    private Boolean isChecked;

    private Notification(User user, String title, String contents, Boolean isChecked) {
        this.user = user;
        this.title = title;
        this.contents = contents;
        this.isChecked = isChecked;
    }

    public static Notification create(User user, String title, String contents) {
        return new Notification(user, title, contents, false);
    }
}
