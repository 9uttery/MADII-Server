package com.guttery.madii.domain.notification.domain.model;

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

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "t_notification")
@Access(AccessType.FIELD)
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    private String title;
    private String contents;
    private Boolean isChecked;
    private LocalDateTime createdAt;

    private Notification(User user, String title, String contents, Boolean isChecked) {
        this.user = user;
        this.title = title;
        this.contents = contents;
        this.isChecked = isChecked;
        this.createdAt = LocalDateTime.now();
    }


    public static Notification create(User user, String title, String contents) {
        return new Notification(user, title, contents, false);
    }

    public static List<Notification> createBulk(List<User> users, String title, String contents) {
        return users.stream()
                .map(user -> new Notification(user, title, contents, false))
                .toList();
    }

    public static List<Notification> createdBulkFormatted(List<User> users, String title, String joyName, String formattedContents) {
        return users.stream()
                .filter(User::hasProfile)
                .map(user -> new Notification(user, title, String.format(formattedContents, user.getNickname(), joyName), false))
                .toList();
    }

    public Long getUserId() {
        return this.user.getUserId();
    }
}
