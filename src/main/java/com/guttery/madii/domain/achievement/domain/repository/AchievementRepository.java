package com.guttery.madii.domain.achievement.domain.repository;

import com.guttery.madii.domain.achievement.domain.model.Achievement;
import com.guttery.madii.domain.joy.domain.model.Joy;
import com.guttery.madii.domain.user.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AchievementRepository extends JpaRepository<Achievement, Long>, AchievementQueryDslRepository {

    boolean existsByJoyAndAchieverAndFinishInfo_IsFinishedFalse(Joy joy, User user);

}
