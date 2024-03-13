package com.guttery.madii.domain.joy.domain.repository;

import com.guttery.madii.domain.joy.application.dto.MostAchievedJoyInfoProjection;
import com.guttery.madii.domain.joy.domain.model.Joy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JoyRepository extends JpaRepository<Joy, Long> {
    String getMostAchievedJoyQuery =
            "SELECT " +
                    "    joyId, " +
                    "    joyIconNum, " +
                    "    contents, " +
                    "    isCreatedByMe, " +
                    "    achieveCount, " +
                    "    achieveRank " +
                    "FROM ( " +
                    "    SELECT " +
                    "        j.joy_id                                                       AS joyId, " +
                    "        j.joy_icon_num                                                 AS joyIconNum, " +
                    "        j.contents                                                     AS contents, " +
                    "        IF(j.creator_id = :userId, true, false)                        AS isCreatedByMe, " +
                    "        COUNT(a.achievement_id)                                        AS achieveCount, " +
                    "        DENSE_RANK() OVER (ORDER BY COUNT(a.achievement_id) DESC)      AS achieveRank " +
                    "    FROM " +
                    "        t_joy j " +
                    "    LEFT JOIN " +
                    "        t_achievement a ON j.joy_id = a.joy_joy_id " +
                    "    WHERE " +
                    "        a.achiever_user_id = :userId " +
                    "        AND" +
                    "        a.is_finished = true         " +
                    "    GROUP BY " +
                    "        j.joy_id " +
                    ") AS rankedJoys " +
                    "WHERE " +
                    "    achieveRank <= 5 AND achieveCount >= 2;";

    @Query(nativeQuery = true, value = getMostAchievedJoyQuery)
    List<MostAchievedJoyInfoProjection> getMostAchievedJoy(Long userId);
}
