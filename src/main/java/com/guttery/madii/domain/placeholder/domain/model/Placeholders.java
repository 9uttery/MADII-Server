package com.guttery.madii.domain.placeholder.domain.model;

import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Document(collection = "placeholders")
public class Placeholders {
    public static final String FIXED_DOCUMENT_ID = "MADII_PLACEHOLDERS_DOCUMENT_ID";

    @Id
    @BsonProperty("_id")
    private String id; // MongoDB 문서 ID
    private String contents;

    private Placeholders(String contents) {
        this.id = FIXED_DOCUMENT_ID; // 고정된 ID 값 할당
        this.contents = contents;
    }

    public static Placeholders create(String contents) {
        return new Placeholders(contents);
    }
}
