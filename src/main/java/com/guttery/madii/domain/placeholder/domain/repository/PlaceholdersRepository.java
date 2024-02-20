package com.guttery.madii.domain.placeholder.domain.repository;

import com.guttery.madii.domain.placeholder.domain.model.Placeholders;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlaceholdersRepository extends MongoRepository<Placeholders, String> {
    
}
