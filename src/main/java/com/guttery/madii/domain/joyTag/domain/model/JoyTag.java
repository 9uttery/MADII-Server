package com.guttery.madii.domain.joyTag.domain.model;

import com.guttery.madii.common.domain.model.BaseTimeEntity;
import com.guttery.madii.domain.joy.domain.model.Joy;
import com.guttery.madii.domain.tag.domain.model.Tag;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "t_joy_tag")
@Access(AccessType.FIELD)
public class JoyTag extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long joyTagId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "joy_id", nullable = false)
    private Joy joy; // 소확행 id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag; // 태그 id

    public JoyTag(Joy joy, Tag tag) {
        this.joy = joy;
        this.tag = tag;
    }

    public static JoyTag createJoyTag(Joy joy, Tag tag) {
        return new JoyTag(joy, tag);
    }
}
