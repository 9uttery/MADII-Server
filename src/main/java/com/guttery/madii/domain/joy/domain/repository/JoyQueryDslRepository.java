package com.guttery.madii.domain.joy.domain.repository;

import com.guttery.madii.domain.joy.application.dto.JoyGetMyAllResponse;

import java.util.List;

public interface JoyQueryDslRepository {
    List<JoyGetMyAllResponse> getMyJoy(Long userId);
}
