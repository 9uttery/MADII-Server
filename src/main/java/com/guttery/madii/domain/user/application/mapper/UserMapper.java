package com.guttery.madii.domain.user.application.mapper;

import com.guttery.madii.domain.user.application.dto.ProfileReadResponse;
import com.guttery.madii.domain.user.domain.model.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserMapper {
    public static ProfileReadResponse toProfileReadResponse(final User user) {
        return new ProfileReadResponse(user.getUserId(), user.getUserProfile().getNickname(), user.getUserProfile().getImage());
    }
}
