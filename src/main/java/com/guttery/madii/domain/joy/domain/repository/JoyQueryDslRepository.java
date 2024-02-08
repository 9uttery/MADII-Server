package com.guttery.madii.domain.joy.domain.repository;

import com.guttery.madii.domain.joy.application.dto.JoyGetMyAllResponse;
import com.guttery.madii.domain.joy.domain.model.Joy;

import java.util.List;

public interface JoyQueryDslRepository {
    List<JoyGetMyAllResponse> getMyJoy(Long userId);

    List<Joy> getRandomOfficialJoys(int amount);

}
