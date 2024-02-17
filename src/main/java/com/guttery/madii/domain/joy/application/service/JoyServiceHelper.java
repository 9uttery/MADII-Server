package com.guttery.madii.domain.joy.application.service;

import com.guttery.madii.common.exception.CustomException;
import com.guttery.madii.common.exception.ErrorDetails;
import com.guttery.madii.domain.joy.domain.model.Joy;
import com.guttery.madii.domain.joy.domain.repository.JoyRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JoyServiceHelper {
    public static Joy findExistingJoy(final JoyRepository joyRepository, final Long joyId) {
        return joyRepository.findById(joyId)
                .orElseThrow(() -> CustomException.of(ErrorDetails.JOY_NOT_FOUND));
    }
}
