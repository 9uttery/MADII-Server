package com.guttery.madii.domain.user.domain.repository;

import com.guttery.madii.domain.user.domain.model.SocialInfo;
import com.guttery.madii.domain.user.domain.model.User;

import java.util.Optional;

public interface UserQueryDslRepository {
    Optional<User> findUserByLoginId(String loginId);

    Optional<User> findUserBySocialInfo(SocialInfo socialInfo);

    boolean existsByLoginId(String loginId);

    boolean existsByUserId(Long userId);
}
