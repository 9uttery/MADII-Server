package com.guttery.madii.domain.achievement.domain.repository;

import com.guttery.madii.domain.achievement.domain.model.UserPlaylists;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserPlaylistsRepository extends MongoRepository<UserPlaylists, Long>, UserPlaylistsMongoTemplateRepository {
}
