package com.guttery.madii.domain.joy.domain.model;

import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.YearMonth;
import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Document(collection = "recommendedJoys")
public class RecommendedJoys {
    @Id
    private YearMonth _id;
    private List<DailyJoy> dailyJoys;

    public static RecommendedJoys createMonthlyRecommendedJoys(YearMonth month, List<Joy> dailyJoys) {
        return new RecommendedJoys(month, dailyJoys.stream().map(DailyJoy::createFromJoy).toList());
    }

    public void updateDailyJoys(List<Joy> joys) {
        this.dailyJoys = joys.stream().map(DailyJoy::createFromJoy).toList();
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class DailyJoy {
        private String key;
        private String contents;
        private String joyIconNum;

        public static DailyJoy createFromJoy(Joy joy) {
            return new DailyJoy(joy.getJoyId().toString(), joy.getContents(), joy.getJoyIconNum().toString());
        }
    }
}
