package com.guttery.madii.domain.achievement.domain.repository;

import com.guttery.madii.domain.achievement.domain.model.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AchievementRepository extends JpaRepository<Achievement, Long>, AchievementQueryDslRepository {
}
