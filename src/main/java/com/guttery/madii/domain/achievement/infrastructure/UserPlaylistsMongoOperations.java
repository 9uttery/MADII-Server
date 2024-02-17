package com.guttery.madii.domain.achievement.infrastructure;

import com.guttery.madii.domain.achievement.domain.repository.UserPlaylistsMongoTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UserPlaylistsMongoOperations implements UserPlaylistsMongoTemplateRepository {
    private static final String ID = "_id";
    private final MongoTemplate template;
//
//    @Override
//    public UpdateResult completeJoy(final Long userId, final String date, final Long joyId) {
//        return null;
//    }
//
//    @Override
//    public UpdateResult cancelJoy(Long userId, LocalDateTime localDateTime, Long joyId) {
//        return null;
//    }
//
//    @Override
//    public void deleteDailyPlaylist(Long userId, LocalDateTime localDateTime) {
//
//    }
}
