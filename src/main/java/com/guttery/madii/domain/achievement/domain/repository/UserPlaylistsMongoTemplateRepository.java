package com.guttery.madii.domain.achievement.domain.repository;

public interface UserPlaylistsMongoTemplateRepository {
    /* Mongo 템플릿 사용해서 3가지 기능 구현 필요
       1. Joy 완료하기 (unfinishedJoyInfos -> finishedJoyInfos)
       2. Joy 완료 취소하기 (finishedJoyInfos -> unfinishedJoyInfos)
       3. 어제와 오늘이 아닌 날짜의 DailyPlaylist 삭제하기 (초기화)
     */

    // 1. Joy 완료하기 (unfinishedJoyInfos -> finishedJoyInfos)
//    UpdateResult completeJoy(final Long userId, final String date, final Long joyId);

    // 2. Joy 완료 취소하기 (finishedJoyInfos -> unfinishedJoyInfos)
//    UpdateResult cancelJoy(final Long userId, final LocalDateTime localDateTime, final Long joyId);

    // 3. 어제와 오늘이 아닌 날짜의 DailyPlaylist 삭제하기 (초기화)
//    void deleteDailyPlaylist(final Long userId, final LocalDateTime localDateTime);
}
