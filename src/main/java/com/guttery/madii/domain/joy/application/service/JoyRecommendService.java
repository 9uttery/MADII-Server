package com.guttery.madii.domain.joy.application.service;

import com.guttery.madii.common.exception.CustomException;
import com.guttery.madii.common.exception.ErrorDetails;
import com.guttery.madii.domain.joy.application.dto.JoyGetTodayResponse;
import com.guttery.madii.domain.joy.domain.model.Joy;
import com.guttery.madii.domain.joy.domain.model.RecommendedJoys;
import com.guttery.madii.domain.joy.domain.repository.JoyQueryDslRepository;
import com.guttery.madii.domain.joy.domain.repository.RecommendedJoysRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class JoyRecommendService {
    private final JoyQueryDslRepository joyQueryDslRepository;
    private final RecommendedJoysRepository recommendedJoysRepository;

    @Scheduled(cron = "0 0 0 1 * ?")
    public void createNewMonthlyRecommendedJoys() {
        final YearMonth yearMonth = YearMonth.now();
        final List<Joy> joys = pickRandomJoysForSpecificMonth(yearMonth);

//        final Optional<RecommendedJoys> existingRecommendedJoys = recommendedJoysRepository.findById(YearMonth.now());

//        if (existingRecommendedJoys.isPresent()) {
//            existingRecommendedJoys.get().updateDailyJoys(joys);
//            recommendedJoysRepository.save(existingRecommendedJoys.get());
//
//            return;
//        } -> MongoDB는 기본적으로 upsert여서 필요 없는 듯 함


        recommendedJoysRepository.save(RecommendedJoys.createMonthlyRecommendedJoys(yearMonth, joys));
    }

    private List<Joy> pickRandomJoysForSpecificMonth(final YearMonth yearMonth) {
        final int amount = yearMonth.lengthOfMonth();

        return joyQueryDslRepository.getRandomOfficialJoys(amount);
    }

    public JoyGetTodayResponse getTodayRecommendedJoy(final String date) {
        final LocalDate targetDate = Optional.ofNullable(date)
                .map(d -> LocalDate.parse(d, DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .orElse(LocalDate.now());

        final RecommendedJoys foundRecommendedJoys = getRecommendedJoysByYearMonth(YearMonth.of(targetDate.getYear(), targetDate.getMonth()));

        if (foundRecommendedJoys.getDailyJoys().size() < targetDate.getDayOfMonth() - 1) {
            throw CustomException.of(ErrorDetails.TODAY_JOY_NOT_FOUND);
        }

        final RecommendedJoys.DailyJoy foundTargetDateRecommendedJoy = foundRecommendedJoys.getDailyJoys().get(targetDate.getDayOfMonth() - 1);

        return JoyGetTodayResponse.of(foundTargetDateRecommendedJoy);
    }

    private RecommendedJoys getRecommendedJoysByYearMonth(YearMonth yearMonth) {
        return recommendedJoysRepository.findById(yearMonth)
                .orElseThrow(() -> CustomException.of(ErrorDetails.TODAY_JOY_NOT_FOUND));
    }
}
