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
import com.guttery.madii.domain.report.domain.model.Report;
import com.guttery.madii.domain.report.domain.repository.ReportRepository;
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

import java.util.*;
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
    private final ReportRepository reportRepository;

    public List<AlbumCreateResponse> createAlbum(AlbumCreateRequest albumCreateRequest, UserPrincipal userPrincipal) {
        final User user = UserServiceHelper.findExistingUser(userRepository, userPrincipal);

        Random random = new Random();

        // 앨범 썸네일 아이콘 번호 1 ~ 24 중 랜덤 생성
        int albumIconNum = 1 + random.nextInt(24); // 1부터 24까지
        int albumColorNum;

        // albumIconNum 범위에 따라 albumColorNum 결정
        if (albumIconNum >= 1 && albumIconNum <= 6) {
            // albumColorNum 2, 3 중 하나여야 함 (주황 아이콘일 때 분홍 배경 제외)
            int[] colors = {2, 3};
            albumColorNum = colors[random.nextInt(colors.length)];
        } else if (albumIconNum >= 7 && albumIconNum <= 12) {
            // albumColorNum 1, 3, 4 중 하나여야 함
            int[] colors = {1, 3, 4};
            albumColorNum = colors[random.nextInt(colors.length)];
        } else if (albumIconNum >= 13 && albumIconNum <= 18) {
            // albumColorNum 1, 2, 4 중 하나여야 함
            int[] colors = {1, 2, 4};
            albumColorNum = colors[random.nextInt(colors.length)];
        } else if (albumIconNum >= 19 && albumIconNum <= 24) {
            // albumColorNum 2, 3 중 하나여야 함 (분홍 아이콘일 때 주황 배경 제외)
            int[] colors = {2, 3};
            albumColorNum = colors[random.nextInt(colors.length)];
        } else {
            // 기본값 설정
            albumColorNum = 1;
        }

        // 여기서 Album 객체 생성 및 나머지 로직 처리
        final Album album = Album.createAlbum(user, albumCreateRequest.name(), albumCreateRequest.description(),
                false, false, false, albumIconNum, albumColorNum);
        albumRepository.save(album);

        List<AlbumCreateResponse> albumCreateResponseList = albumQueryDslRepository.getMyAlbums(user.getUserId());
        return albumCreateResponseList;
    }

    public void addJoyToAlbum(Long joyId, AlbumSaveJoyRequest albumSaveJoyRequest, UserPrincipal userPrincipal) {
        final User user = UserServiceHelper.findExistingUser(userRepository, userPrincipal);

        for (Long albumId : albumSaveJoyRequest.albumIds()) {
            final Joy joy = joyRepository.findById(joyId)
                    .orElseThrow(() -> CustomException.of(ErrorDetails.JOY_NOT_FOUND));
            final Album album = albumRepository.findById(albumId)
                    .orElseThrow(() -> CustomException.of(ErrorDetails.ALBUM_NOT_FOUND));

            // 앨범 내에서 가장 높은 order 값을 조회하여 1을 더해줌
            final Integer joyOrder = savingJoyRepository.findTopByAlbumOrderByJoyOrderDesc(album).map(SavingJoy::getJoyOrder).orElse(0) + 1;
            final SavingJoy savingJoy = SavingJoy.createSavingJoy(joy, album, joyOrder);
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

        if (album.getAlbumStatus().getIsOfficial()) throw CustomException.of(ErrorDetails.ALREADY_OFFICIAL);

        album.makeOfficial();
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
            albumGetDetailResponse = new AlbumGetDetailResponse(album.getAlbumStatus().getIsOfficial(), album.getAlbumInfo().getAlbumIconNum(), album.getAlbumInfo().getAlbumColorNum(), null, album.getName(), null, album.getDescription(), joyInfoList);
        } else { // 남 앨범
            Boolean isAlbumSaved = albumQueryDslRepository.getIsAlbumSaved(albumId, user.getUserId());
            if (isAlbumSaved) { // 저장 -> joyInfoList 속 isJoySaved 전부 true
                joyInfoList = albumQueryDslRepository.getSavedAlbumJoys(albumId, user.getUserId());
            } else {
                joyInfoList = albumQueryDslRepository.getAlbumJoys(albumId, user.getUserId());
            }
            albumGetDetailResponse = new AlbumGetDetailResponse(album.getAlbumStatus().getIsOfficial(), album.getAlbumInfo().getAlbumIconNum(), album.getAlbumInfo().getAlbumColorNum(), isAlbumSaved, album.getName(), album.getUser().getUserProfile().getNickname(), album.getDescription(), joyInfoList);
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
    public List<AlbumGetAllResponse> getAllIsOfficialAlbums() {
        List<AlbumGetAllResponse> getAllResponseList = albumQueryDslRepository.getAllIsOfficialAlbums();
        return getAllResponseList;
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

        // 최근 본 앨범 레디스 캐싱에서도 삭제
        // Redis의 모든 키를 가져와서 해당 패턴을 가진 키들을 찾음
        Set<String> keys = redisTemplate.keys(KEY_PREFIX + "*");

        // 각 키에 대해 작업을 수행
        for (String key : keys) {
            ListOperations<String, String> listOps = redisTemplate.opsForList();
            List<String> albumIds = listOps.range(key, 0, -1);

            // 특정 앨범 ID를 포함하고 있는지 확인하고 포함하고 있다면 삭제
            if (albumIds.contains("albumId:" + albumId)) {
                listOps.remove(key, 0, "albumId:" + albumId);
            }
        }

        savingAlbumRepository.deleteByAlbumAlbumId(albumId);
        savingJoyRepository.deleteByAlbumAlbumId(albumId);
        albumRepository.deleteById(albumId);
    }

    public List<AlbumGetCreatedResponse> getCreatedAlbums(UserPrincipal userPrincipal) {
        final User user = UserServiceHelper.findExistingUser(userRepository, userPrincipal);

        List<AlbumGetCreatedResponse> albumGetCreatedResponseList = albumQueryDslRepository.getMyAlbumsCreated(user.getUserId());
        return albumGetCreatedResponseList;
    }

    public void reportAlbum(AlbumReportRequest albumReportRequest, Long albumId, UserPrincipal userPrincipal) {
        final User user = UserServiceHelper.findExistingUser(userRepository, userPrincipal);
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> CustomException.of(ErrorDetails.ALBUM_NOT_FOUND));

        final Report report = Report.createReport(user, album, albumReportRequest.contents());
        reportRepository.save(report);
        album.makeBlocked();
    }

    public void putAllAlbum(AlbumPutAllRequest albumPutAllRequest, Long albumId, UserPrincipal userPrincipal) {
        final User user = UserServiceHelper.findExistingUser(userRepository, userPrincipal);
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> CustomException.of(ErrorDetails.ALBUM_NOT_FOUND));

        String name = albumPutAllRequest.name();
        String description = albumPutAllRequest.description();
        List<SavingJoyDto> joys = albumPutAllRequest.joys();
        List<Long> deletedJoyIds = albumPutAllRequest.deletedJoyIds();

        // 1. 삭제된 소확행 처리
        if (deletedJoyIds != null && !deletedJoyIds.isEmpty()) {
            savingJoyRepository.deleteAllByJoyJoyIdIn(deletedJoyIds);
        }

        for (SavingJoyDto joyDto :joys) {
            // 2. 수정된 소확행 처리
            if (joyDto.joyId() != null) {
                // 소확행명 수정 - joy table update
                Joy joy = joyRepository.findById(joyDto.joyId())
                        .orElseThrow(() -> CustomException.of(ErrorDetails.JOY_NOT_FOUND));
                joy.modifyContents(joyDto.contents());

                // 순서 세팅 - saving_joy table update
                SavingJoy savingJoy = savingJoyRepository.findByJoyAndAlbum(joy, album)
                        .orElseThrow(() -> CustomException.of(ErrorDetails.SAVING_JOY_NOT_FOUND));
                savingJoy.modifyJoyOrder(joyDto.joyOrder());
            } else {
                // 3. 새로 추가된 소확행 처리
                Random random = new Random();
                int bound = 24; // 소확행 썸네일 아이콘 번호 1 ~ 24 중 랜덤 생성

                Joy joy;
                if (album.getAlbumStatus().getIsOfficial()) {
                    joy = Joy.createOfficialJoy(user, 1 + random.nextInt(bound), joyDto.contents());
                } else {
                    joy = Joy.createPersonalJoy(user, 1 + random.nextInt(bound), joyDto.contents());
                }

                joyRepository.save(joy);
                SavingJoy savingJoy = SavingJoy.createSavingJoy(joy, album, joyDto.joyOrder());
                savingJoyRepository.save(savingJoy);
            }
        }

        album.modifyNameAndDes(name, description);
    }
}
