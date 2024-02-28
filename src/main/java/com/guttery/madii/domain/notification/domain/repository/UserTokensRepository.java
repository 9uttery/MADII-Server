package com.guttery.madii.domain.notification.domain.repository;

import com.guttery.madii.domain.notification.domain.model.UserTokens;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserTokensRepository extends MongoRepository<UserTokens, String> {

}
