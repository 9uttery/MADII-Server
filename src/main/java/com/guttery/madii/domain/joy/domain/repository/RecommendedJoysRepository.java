package com.guttery.madii.domain.joy.domain.repository;

import com.guttery.madii.domain.joy.domain.model.RecommendedJoys;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.YearMonth;

public interface RecommendedJoysRepository extends MongoRepository<RecommendedJoys, YearMonth> {

}
