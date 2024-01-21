package com.guttery.madii.domain.albums.application.service;

import com.guttery.madii.common.exception.CustomException;
import com.guttery.madii.common.exception.ErrorDetails;
import com.guttery.madii.domain.albums.application.dto.AlbumCreateRequest;
import com.guttery.madii.domain.albums.application.dto.AlbumCreateResponse;
import com.guttery.madii.domain.albums.application.dto.AlbumPutJoyRequest;
import com.guttery.madii.domain.albums.application.dto.AlbumSaveJoyRequest;
import com.guttery.madii.domain.albums.domain.model.Album;
import com.guttery.madii.domain.albums.domain.model.SavingJoy;
import com.guttery.madii.domain.albums.domain.repository.AlbumQueryDslRepository;
import com.guttery.madii.domain.albums.domain.repository.AlbumRepository;
import com.guttery.madii.domain.albums.domain.repository.SavingAlbumRepository;
import com.guttery.madii.domain.joy.domain.model.Joy;
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
public class AlbumService {
    private final AlbumRepository albumRepository;
    private final AlbumQueryDslRepository albumQueryDslRepository;
    private final UserRepository userRepository;
    private final JoyRepository joyRepository;
    private final SavingAlbumRepository savingAlbumRepository;

    public List<AlbumCreateResponse> createAlbum(AlbumCreateRequest albumCreateRequest, UserPrincipal userPrincipal) {
        final User user = UserServiceHelper.findExistingUser(userRepository, userPrincipal);

        // 앨범 썸네일 아이콘 번호 0 ~ 17 중 랜덤 생성
        Random random = new Random();
        int iconBound = 18;
        // 앨범 배경색 번호 0 ~ 3 중 랜덤 생성
        int colorBound = 4;

        final Album album = Album.createAlbum(user, albumCreateRequest.name(), albumCreateRequest.description(),
                false, false, false, random.nextInt(iconBound), random.nextInt(colorBound));
        albumRepository.save(album);

        List<AlbumCreateResponse> albumCreateResponseList = albumQueryDslRepository.getMyAlbums(user.getUserId());
        return albumCreateResponseList;
    }

    public void addJoyToAlbum(Long joyId, AlbumSaveJoyRequest albumSaveJoyRequest, UserPrincipal userPrincipal) {
        final User user = UserServiceHelper.findExistingUser(userRepository, userPrincipal);

        for (Long albumId : albumSaveJoyRequest.albumIds()) {
            final Joy joy =  joyRepository.findById(joyId)
                    .orElseThrow(() -> CustomException.of(ErrorDetails.JOY_NOT_FOUND));
            final Album album = albumRepository.findById(albumId)
                    .orElseThrow(() -> CustomException.of(ErrorDetails.ALBUN_NOT_FOUND));
            final SavingJoy savingJoy = SavingJoy.createSavingJoy(joy, album);
            savingAlbumRepository.save(savingJoy);
        }
    }

    public void putMyAlbum(Long albumId, AlbumPutJoyRequest albumPutJoyRequest, UserPrincipal userPrincipal) {
        final User user = UserServiceHelper.findExistingUser(userRepository, userPrincipal);
        final Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> CustomException.of(ErrorDetails.ALBUN_NOT_FOUND));

        album.modifyNameAndDes(albumPutJoyRequest.name(), albumPutJoyRequest.description());
    }
}
