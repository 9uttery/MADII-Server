package com.guttery.madii.domain.joy.application.service;

import com.guttery.madii.common.exception.CustomException;
import com.guttery.madii.common.exception.ErrorDetails;
import com.guttery.madii.domain.albums.domain.model.Album;
import com.guttery.madii.domain.albums.domain.model.SavingJoy;
import com.guttery.madii.domain.albums.domain.repository.AlbumRepository;
import com.guttery.madii.domain.albums.domain.repository.SavingJoyRepository;
import com.guttery.madii.domain.joy.application.dto.*;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class JoyService {
    private final JoyRepository joyRepository;
    private final JoyQueryDslRepository joyQueryDslRepository;
    private final UserRepository userRepository;
    private final AlbumRepository albumRepository;
    private final SavingJoyRepository savingJoyRepository;

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

    @Transactional(readOnly = true)
    public List<JoyGetMyAllResponse> getMyJoy(UserPrincipal userPrincipal) {
        final User user = UserServiceHelper.findExistingUser(userRepository, userPrincipal);

        List<JoyGetMyAllResponse> joyGetMyAllResponseList = joyQueryDslRepository.getMyJoy(user.getUserId());
        return joyGetMyAllResponseList;
    }

    public void deleteFromSavingJoy(Long joyId, List<Long> beforeAlbumIds) {
        for (Long albumId : beforeAlbumIds) {
            final Joy joy =  joyRepository.findById(joyId)
                    .orElseThrow(() -> CustomException.of(ErrorDetails.JOY_NOT_FOUND));
            final Album album = albumRepository.findById(albumId)
                    .orElseThrow(() -> CustomException.of(ErrorDetails.ALBUM_NOT_FOUND));
            final SavingJoy savingJoy = savingJoyRepository.findByJoyAndAlbum(joy, album);
            savingJoyRepository.delete(savingJoy);
        }
    }

    public void addToSavingJoy(Long joyId, List<Long> afterAlbumIds) {
        for (Long albumId : afterAlbumIds) {
            final Joy joy =  joyRepository.findById(joyId)
                    .orElseThrow(() -> CustomException.of(ErrorDetails.JOY_NOT_FOUND));
            final Album album = albumRepository.findById(albumId)
                    .orElseThrow(() -> CustomException.of(ErrorDetails.ALBUM_NOT_FOUND));
            final SavingJoy savingJoy = SavingJoy.createSavingJoy(joy, album);
            savingJoyRepository.save(savingJoy);
        }
    }

    public JoyPutResponse putMyJoy(Long joyId, final JoyPutRequest joyPutRequest, UserPrincipal userPrincipal) {
        final User user = UserServiceHelper.findExistingUser(userRepository, userPrincipal);
        final Joy joy = joyRepository.findById(joyId)
                .orElseThrow(() -> CustomException.of(ErrorDetails.JOY_NOT_FOUND));

        JoyPutResponse joyPutResponse = null;
        if (joy.getUser().getUserId().equals(user.getUserId())) { // 내가 기록한 소확행
            System.out.println("내가 기록한 소확행 수정");
            // 1. 단순 contents 수정
            joy.modifyContents(joyPutRequest.contents());
            joyPutResponse = new JoyPutResponse(joy.getJoyIconNum(), joy.getContents());

            // 2. 수정 전 - 소확행 포함 앨범 목록 -> 저장 취소
            deleteFromSavingJoy(joyId, joyPutRequest.beforeAlbumIds());

            // 3. 수정 후 - 소확행 포함 앨범 목록 -> 저장
            addToSavingJoy(joyId, joyPutRequest.afterAlbumIds());

        } else { // 남이 기록한 소확행
            if (joy.getContents().equals(joyPutRequest.contents())) { // 내용 수정 X -> 남이 기록한 소확행 그대로, 저장되는 앨범만 변경
                System.out.println("남이 기록한 소확행 내용 수정 X");
                joyPutResponse = new JoyPutResponse(joy.getJoyIconNum(), joy.getContents());

                // 1. 수정 전 - 소확행 포함 앨범 목록 -> 저장 취소
                deleteFromSavingJoy(joyId, joyPutRequest.beforeAlbumIds());

                // 2. 수정 후 - 소확행 포함 앨범 목록 -> 저장
                addToSavingJoy(joyId, joyPutRequest.afterAlbumIds());
            } else { // 내용 수정 O -> 내가 기록한 소확행 추가
                System.out.println("남이 기록한 소확행 내용 수정 O");
                // 1. 수정 전 -소확행 포함 앨범 목록 -> 저장 취소
                deleteFromSavingJoy(joyId, joyPutRequest.beforeAlbumIds());

                // 2. 내가 기록한 소확행으로 추가
                // 소확행 썸네일 아이콘 번호 0 ~ 17 중 랜덤 생성
                Random random = new Random();
                int bound = 18;

                final Joy newJoy = Joy.createPersonalJoy(user, random.nextInt(bound), joyPutRequest.contents());
                joyRepository.save(newJoy);
                joyPutResponse = new JoyPutResponse(newJoy.getJoyIconNum(), newJoy.getContents());

                // 3. 수정 후 - 소확행 포함 앨범 목록 -> 저장
                addToSavingJoy(newJoy.getJoyId(), joyPutRequest.afterAlbumIds());
            }
        }

        return joyPutResponse;
    }

    public void deleteMyJoy(Long joyId, UserPrincipal userPrincipal) {
        final User user = UserServiceHelper.findExistingUser(userRepository, userPrincipal);
        final Joy joy = joyRepository.findById(joyId)
                .orElseThrow(() -> CustomException.of(ErrorDetails.JOY_NOT_FOUND));

        if (joy.getUser().getUserId().equals(user.getUserId())) { // 내가 기록한 소확행
            joyRepository.delete(joy);
        } else {
            // 1. 북마크 해제 -> * 북마크 기능 후 구현 완료하기

        }

    }
}
