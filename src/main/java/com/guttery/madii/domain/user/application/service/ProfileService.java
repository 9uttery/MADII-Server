package com.guttery.madii.domain.user.application.service;

import com.guttery.madii.domain.user.application.dto.ProfileReadResponse;
import com.guttery.madii.domain.user.application.dto.ProfileUpdateRequest;
import com.guttery.madii.domain.user.application.dto.UpdateMarketingAgreementRequest;
import com.guttery.madii.domain.user.application.mapper.UserMapper;
import com.guttery.madii.domain.user.domain.model.User;
import com.guttery.madii.domain.user.domain.model.UserPrincipal;
import com.guttery.madii.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class ProfileService {
    private final UserRepository userRepository;

    public ProfileReadResponse readProfile(UserPrincipal userPrincipal) {
        final User user = UserServiceHelper.findExistingUser(userRepository, userPrincipal);

        return UserMapper.toProfileReadResponse(user);
    }

    public void updateProfile(final ProfileUpdateRequest profileUpdateRequest, UserPrincipal userPrincipal) {
        final User user = UserServiceHelper.findExistingUser(userRepository, userPrincipal);
        user.updateUserProfile(profileUpdateRequest.nickname(), profileUpdateRequest.image());
        userRepository.save(user);
    }

    public void updateMarketingAgreement(final UpdateMarketingAgreementRequest updateMarketingAgreementRequest, final UserPrincipal userPrincipal) {
        final User user = UserServiceHelper.findExistingUser(userRepository, userPrincipal);
        user.updateMarketingAgreement(updateMarketingAgreementRequest.agreesMarketing());
        userRepository.save(user);
    }
}
