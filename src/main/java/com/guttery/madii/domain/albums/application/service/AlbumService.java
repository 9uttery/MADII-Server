package com.guttery.madii.domain.albums.application.service;

import com.guttery.madii.common.exception.CustomException;
import com.guttery.madii.common.exception.ErrorDetails;
import com.guttery.madii.domain.albums.application.dto.*;
import com.guttery.madii.domain.albums.domain.model.Album;
import com.guttery.madii.domain.albums.domain.model.SavingAlbum;
import com.guttery.madii.domain.albums.domain.model.SavingJoy;
import com.guttery.madii.domain.albums.domain.repository.AlbumQueryDslRepository;
import com.guttery.madii.domain.albums.domain.repository.AlbumRepository;
import com.guttery.madii.domain.albums.domain.repository.SavingAlbumRepository;
import com.guttery.madii.domain.albums.domain.repository.SavingJoyRepository;
import com.guttery.madii.domain.joy.domain.model.Joy;
import com.guttery.madii.domain.joy.domain.repository.JoyRepository;
import com.guttery.madii.domain.user.application.service.UserServiceHelper;
import com.guttery.madii.domain.user.domain.model.User;
import com.guttery.madii.domain.user.domain.model.UserPrincipal;
import com.guttery.madii.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class AlbumService {
    private static final String KEY_PREFIX = "recentAlbums:";
    private static final String VALUE_PREFIX = "albumId:";
    private static final int MAX_RECENT_ALBUMS = 10;
    private final RedisTemplate<String, String> redisTemplate;
    private final AlbumRepository albumRepository;
    private final AlbumQueryDslRepository albumQueryDslRepository;
    private final UserRepository userRepository;
    private final JoyRepository joyRepository;
    private final SavingAlbumRepository savingAlbumRepository;
    private final SavingJoyRepository savingJoyRepository;

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
                    .orElseThrow(() -> CustomException.of(ErrorDetails.ALBUM_NOT_FOUND));
            final SavingJoy savingJoy = SavingJoy.createSavingJoy(joy, album);
            savingJoyRepository.save(savingJoy);
        }
    }

    public void putMyAlbum(Long albumId, AlbumPutRequest albumPutRequest, UserPrincipal userPrincipal) {
        final User user = UserServiceHelper.findExistingUser(userRepository, userPrincipal);
        final Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> CustomException.of(ErrorDetails.ALBUM_NOT_FOUND));

        album.modifyNameAndDes(albumPutRequest.name(), albumPutRequest.description());
    }

    public void putMyAlbumStatus(Long albumId, UserPrincipal userPrincipal) {
        final User user = UserServiceHelper.findExistingUser(userRepository, userPrincipal);
        final Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> CustomException.of(ErrorDetails.ALBUM_NOT_FOUND));

        if (album.getAlbumStatus().getIsOfficial()) {
            album.makePersonal();
        } else {
            album.makeOfficial();
        }
    }

    @Transactional(readOnly = true)
    public AlbumGetDetailResponse getAlbumDetail(Long albumId, UserPrincipal userPrincipal) {
        final User user = UserServiceHelper.findExistingUser(userRepository, userPrincipal);
        final Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> CustomException.of(ErrorDetails.ALBUM_NOT_FOUND));

        AlbumGetDetailResponse albumGetDetailResponse;
        List<JoyGetInfo> joyInfoList;
        if (album.getUser().getUserId().equals(user.getUserId())) { // 내 앨범
            joyInfoList = albumQueryDslRepository.getMyAlbumJoys(albumId);
            albumGetDetailResponse = new AlbumGetDetailResponse(null, album.getName(), null, album.getDescription(), joyInfoList);
        } else { // 남 앨범
            joyInfoList = albumQueryDslRepository.getAlbumJoys(albumId, user.getUserId());
            Boolean isAlbumSaved = albumQueryDslRepository.getIsAlbumSaved(albumId, user.getUserId());
            albumGetDetailResponse = new AlbumGetDetailResponse(isAlbumSaved, album.getName(), album.getUser().getUserProfile().getNickname(), album.getDescription(), joyInfoList);
        }

        return albumGetDetailResponse;
    }

    public Boolean bookmarkStatus(User user, Album album) {
        return savingAlbumRepository.existsByUserAndAlbum(user, album);
    }

    public void createAlbumBookmark(Long albumId, UserPrincipal userPrincipal) {
        final User user = UserServiceHelper.findExistingUser(userRepository, userPrincipal);
        final Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> CustomException.of(ErrorDetails.ALBUM_NOT_FOUND));

        if (bookmarkStatus(user, album)) throw CustomException.of(ErrorDetails.ALREADY_EXIST_BOOKMARK);

        final SavingAlbum savingAlbum = SavingAlbum.createSavingAlbum(user, album);
        savingAlbumRepository.save(savingAlbum);
    }

    public void deleteAlbumBookmark(Long albumId, UserPrincipal userPrincipal) {
        final User user = UserServiceHelper.findExistingUser(userRepository, userPrincipal);
        final Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> CustomException.of(ErrorDetails.ALBUM_NOT_FOUND));

        if (!bookmarkStatus(user, album)) throw CustomException.of(ErrorDetails.NOT_FOUND_BOOKMARK);

        savingAlbumRepository.deleteByUserAndAlbum(user, album);
    }

    @Transactional(readOnly = true)
    public List<AlbumGetMyAllResponse> getMyAllAlbums(UserPrincipal userPrincipal) {
        final User user = UserServiceHelper.findExistingUser(userRepository, userPrincipal);

        List<AlbumGetMyAllResponse> getMyAlbumsInfoList = albumQueryDslRepository.getMyAlbumsInfo(user.getUserId());
        List<AlbumGetMyAllResponse> getMyBookmarksInfoList = albumQueryDslRepository.getMyBookmarksInfo(user.getUserId());
        List<AlbumGetMyAllResponse> albumGetMyAllResponseList = new ArrayList<>();
        albumGetMyAllResponseList.addAll(getMyAlbumsInfoList);
        albumGetMyAllResponseList.addAll(getMyBookmarksInfoList);
        albumGetMyAllResponseList.sort(Comparator.comparing(AlbumGetMyAllResponse::modifiedAt).reversed());
        return albumGetMyAllResponseList;
    }

    @Transactional(readOnly = true)
    public List<AlbumGetJoyAllResponse> getMyJoyAllAlbums(Long joyId, UserPrincipal userPrincipal) {
        final User user = UserServiceHelper.findExistingUser(userRepository, userPrincipal);

        List<AlbumGetJoyAllResponse> getJoyAllResponseList = albumQueryDslRepository.getMyJoyAllAlbums(joyId, user.getUserId());
        return getJoyAllResponseList;
    }

    private <T> Slice<T> toSlice(List<T> content, Pageable pageable) {
        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            content.remove(content.size() - 1);
            hasNext = true;
        }
        return new SliceImpl<>(content, pageable, hasNext);
    }

    @Transactional(readOnly = true)
    public Slice<AlbumGetAllResponse> getAllAlbums(Long albumId, int size) {
        List<AlbumGetAllResponse> getAllResponseList = albumQueryDslRepository.getAllAlbums(albumId, size);
        return toSlice(getAllResponseList, PageRequest.of(0, size));
    }

    @Transactional(readOnly = true)
    public List<AlbumGetOthersResponse> getOtherAlbums(Long albumId, UserPrincipal userPrincipal) {
        final User user = UserServiceHelper.findExistingUser(userRepository, userPrincipal);

        List<AlbumGetOthersResponse> getOthersResponseList = albumQueryDslRepository.getOtherAlbums(albumId, user.getUserId());
        return getOthersResponseList;
    }

    public void createRecentAlbums(Long albumId, UserPrincipal userPrincipal) {
        final User user = UserServiceHelper.findExistingUser(userRepository, userPrincipal);

        // 앨범 소유자 확인
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> CustomException.of(ErrorDetails.ALBUM_NOT_FOUND));
        if (album.getUser().getUserId().equals(user.getUserId())) {
            // 사용자가 만든 앨범인 경우 로직을 수행하지 않음
            return;
        }

        String key = KEY_PREFIX + user.getUserId();
        String value = VALUE_PREFIX + albumId;
        ListOperations<String, String> listOps = redisTemplate.opsForList();

        // 중복 제거
        listOps.remove(key, 0, value);

        // 앨범 ID 추가
        listOps.leftPush(key, value);

        // 리스트 크기 제한
        if (listOps.size(key) > MAX_RECENT_ALBUMS) {
            listOps.trim(key, 0, MAX_RECENT_ALBUMS - 1);
        }
    }

    private Album getAlbumByAlbumId(Long albumId) {
        return albumRepository.findById(albumId)
                .orElseThrow(() -> CustomException.of(ErrorDetails.ALBUM_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public List<AlbumGetRecentResponse> getRecentAlbums(UserPrincipal userPrincipal) {
        final User user = UserServiceHelper.findExistingUser(userRepository, userPrincipal);

        String key = KEY_PREFIX + user.getUserId();
        ListOperations<String, String> listOps = redisTemplate.opsForList();
        List<String> albumIdsInRedis = listOps.range(key, 0, -1);
//        System.out.println("Redis -> " + albumIdsInRedis);

        List<AlbumGetRecentResponse> albumGetRecentResponseList = albumIdsInRedis.stream()
                .map(s -> s.replace("albumId:", ""))
                .map(Long::parseLong)
                .map(this::getAlbumByAlbumId)
                .map(album -> new AlbumGetRecentResponse(album.getAlbumId(), album.getAlbumInfo().getAlbumIconNum(), album.getAlbumInfo().getAlbumColorNum(), album.getName()))
                .collect(Collectors.toList());

        return albumGetRecentResponseList;
    }

    public void deleteAlbum(Long albumId, UserPrincipal userPrincipal) {
        final User user = UserServiceHelper.findExistingUser(userRepository, userPrincipal);

        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> CustomException.of(ErrorDetails.ALBUM_NOT_FOUND));
        if (!album.getUser().getUserId().equals(user.getUserId())) {
            throw CustomException.of(ErrorDetails.NOT_MY_ALBUM);
        }

        savingAlbumRepository.deleteByAlbumAlbumId(albumId);
        savingJoyRepository.deleteByAlbumAlbumId(albumId);
        albumRepository.deleteById(albumId);
    }
}
