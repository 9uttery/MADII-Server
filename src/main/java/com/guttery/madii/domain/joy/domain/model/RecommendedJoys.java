package com.guttery.madii.domain.joy.domain.model;

import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.YearMonth;
import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Document(collection = "recommendedJoys")
public class RecommendedJoys {
    @Id
    @BsonProperty("_id")
    private YearMonth id;
    private List<DailyJoy> dailyJoys;

    public static RecommendedJoys createMonthlyRecommendedJoys(YearMonth month, List<Joy> dailyJoys) {
        return new RecommendedJoys(month, dailyJoys.stream().map(DailyJoy::createFromJoy).toList());
    }

    public void updateDailyJoys(List<Joy> joys) {
        this.dailyJoys = joys.stream().map(DailyJoy::createFromJoy).toList();
    }
}
