package com.guttery.madii.domain.achievement.domain.model;

import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Document(collection = "userPlaylists")
public class UserPlaylists {
    @Id
    @BsonProperty("_id")
    private Long id; // UserId를 PK로 사용

    private List<DailyPlaylist> dailyPlaylists;

    public static UserPlaylists createEmptyUserPlaylists(Long userId) {
        return new UserPlaylists(userId, new ArrayList<>());
    }

    /* Mongo 템플릿 사용해서 3가지 기능 구현 필요
       1. Joy 완료하기 (unfinishedJoyInfos -> finishedJoyInfos)
       2. Joy 완료 취소하기 (finishedJoyInfos -> unfinishedJoyInfos)
       3. 어제와 오늘이 아닌 날짜의 DailyPlaylist 삭제하기 (초기화)
     */
}
