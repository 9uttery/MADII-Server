package com.guttery.madii.domain.tag.domain.model;

import com.guttery.madii.common.domain.model.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "t_tag")
@Access(AccessType.FIELD)
public class Tag extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagId;
    @Enumerated(EnumType.STRING)
    private TagType tagType; // 태그 타입 - 취향저격 소확행 옵션 (어떤 행복, 누구와, 어떤 날씨)
    private String contents; // 태그 내용 (현재는 9개 고정)

    public Tag(TagType tagType, String contents) {
        this.tagType = tagType;
        this.contents = contents;
    }

    public static Tag createTag(TagType tagType, String contents) {
        return new Tag(tagType, contents);
    }
}
