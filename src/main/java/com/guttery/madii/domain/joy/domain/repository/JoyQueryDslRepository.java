package com.guttery.madii.domain.joy.domain.repository;

import com.guttery.madii.domain.joy.application.dto.JoyGetDetailResponse;
import com.guttery.madii.domain.joy.application.dto.JoyGetMyAllResponse;
import com.guttery.madii.domain.joy.application.dto.JoyGetRecommendRequest;
import com.guttery.madii.domain.joy.application.dto.JoyGetRecommendResponse;
import com.guttery.madii.domain.joy.domain.model.Joy;

import java.util.List;

public interface JoyQueryDslRepository {
    List<JoyGetMyAllResponse> getMyJoy(Long userId);

    List<Joy> getRandomOfficialJoys(int amount);

    List<JoyGetRecommendResponse> getJoyRecommend(JoyGetRecommendRequest joyGetRecommendRequest, Long userId);

    JoyGetDetailResponse getJoyDetail(Long joyId, Long userId);
}
