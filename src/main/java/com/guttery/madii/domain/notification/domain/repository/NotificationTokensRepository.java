package com.guttery.madii.domain.notification.domain.repository;

import com.guttery.madii.domain.notification.domain.model.NotificationTokens;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationTokensRepository extends MongoRepository<NotificationTokens, String> {

}
