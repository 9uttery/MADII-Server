package com.guttery.madii.domain.joy.application.service;

import com.guttery.madii.domain.joy.application.dto.JoyCreateRequest;
import com.guttery.madii.domain.joy.application.dto.JoyCreateResponse;
import com.guttery.madii.domain.joy.application.dto.JoyGetMyAllResponse;
import com.guttery.madii.domain.joy.domain.model.Joy;
import com.guttery.madii.domain.joy.domain.repository.JoyQueryDslRepository;
import com.guttery.madii.domain.joy.domain.repository.JoyRepository;
import com.guttery.madii.domain.user.application.service.UserServiceHelper;
import com.guttery.madii.domain.user.domain.model.User;
import com.guttery.madii.domain.user.domain.model.UserPrincipal;
import com.guttery.madii.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Slf4j
@Service
public class JoyService {
    private final JoyRepository joyRepository;
    private final JoyQueryDslRepository joyQueryDslRepository;
    private final UserRepository userRepository;

    public JoyCreateResponse createJoy(final JoyCreateRequest joyCreateRequest, UserPrincipal userPrincipal) {
        final User user = UserServiceHelper.findExistingUser(userRepository, userPrincipal);

        // 소확행 썸네일 아이콘 번호 0 ~ 17 중 랜덤 생성
        Random random = new Random();
        int bound = 18;

        final Joy joy = Joy.createPersonalJoy(user, random.nextInt(bound), joyCreateRequest.contents());
        joyRepository.save(joy);

        JoyCreateResponse joyCreateResponse = new JoyCreateResponse(joy.getJoyIconNum(), joy.getContents());
        return joyCreateResponse;
    }

    public List<JoyGetMyAllResponse> getMyJoy(UserPrincipal userPrincipal) {
        final User user = UserServiceHelper.findExistingUser(userRepository, userPrincipal);

        List<JoyGetMyAllResponse> joyGetMyAllResponseList = joyQueryDslRepository.getMyJoy(user.getUserId());
        return joyGetMyAllResponseList;
    }
}
