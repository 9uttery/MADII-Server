package com.guttery.madii.domain.report.domain.model;

import com.guttery.madii.common.domain.model.BaseTimeEntity;
import com.guttery.madii.domain.albums.domain.model.Album;
import com.guttery.madii.domain.user.domain.model.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "t_report")
@Access(AccessType.FIELD)
public class Report extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id", nullable = false)
    private Album album; // 앨범 id
    @Column(length = 250)
    private String contents; // 신고 이유

    public Report(User user, Album album, String contents) {
        this.user = user;
        this.album = album;
        this.contents = contents;
    }

    public static Report createReport(User user, Album album, String contents) {
        return new Report(user, album, contents);
    }
}
