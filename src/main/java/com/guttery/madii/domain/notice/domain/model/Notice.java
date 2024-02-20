package com.guttery.madii.domain.notice.domain.model;

import com.guttery.madii.common.domain.model.BaseTimeEntity;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "t_notice")
@Access(AccessType.FIELD)
public class Notice extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeId;
    private String title;
    @Column(name = "contents", columnDefinition = "TEXT")
    private String contents;

    public Notice(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public static Notice create(String title, String contents) {
        return new Notice(title, contents);
    }
}
