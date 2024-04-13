package com.guttery.madii.domain.albums.infrastructure;

import com.guttery.madii.domain.albums.application.dto.*;
import com.guttery.madii.domain.albums.domain.repository.AlbumQueryDslRepository;
import com.querydsl.core.types.NullExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.guttery.madii.domain.albums.domain.model.QAlbum.album;
import static com.guttery.madii.domain.albums.domain.model.QSavingAlbum.savingAlbum;
import static com.guttery.madii.domain.albums.domain.model.QSavingJoy.savingJoy;
import static com.guttery.madii.domain.joy.domain.model.QJoy.joy;

@Repository
@RequiredArgsConstructor
@Slf4j
public class AlbumRepositoryImpl implements AlbumQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<AlbumCreateResponse> getMyAlbums(Long userId) {
        return queryFactory
                .select(Projections.constructor(AlbumCreateResponse.class,
                        album.albumId,
                        album.name))
                .from(album)
                .where(album.user.userId.eq(userId))
                .orderBy(album.createdAt.desc())
                .fetch();
    }

    @Override
    public List<JoyGetInfo> getMyAlbumJoys(Long albumId) {
        return queryFactory
                .select(Projections.constructor(JoyGetInfo.class,
                        joy.joyId,
                        joy.joyIconNum,
                        joy.contents,
                        new NullExpression<>(Boolean.class)))
                .from(joy)
                .join(savingJoy)
                .on(savingJoy.joy.joyId.eq(joy.joyId)
                        .and(savingJoy.album.albumId.eq(albumId)))
                .orderBy(savingJoy.createdAt.desc())
                .fetch();
    }

    @Override
    public List<JoyGetInfo> getAlbumJoys(Long albumId, Long userId) {
        // 사용자의 앨범에 저장된 joys
        JPQLQuery<Boolean> isJoySavedSubQuery = JPAExpressions
                .select(savingJoy.isNotNull())
                .from(savingJoy)
                .where(savingJoy.joy.joyId.eq(joy.joyId),
                        savingJoy.album.user.userId.eq(userId));

        return queryFactory
                .select(Projections.constructor(JoyGetInfo.class,
                        joy.joyId,
                        joy.joyIconNum,
                        joy.contents,
                        isJoySavedSubQuery.exists()))
                .from(joy)
                .join(savingJoy)
                .on(savingJoy.joy.joyId.eq(joy.joyId)
                        .and(savingJoy.album.albumId.eq(albumId)))
                .orderBy(savingJoy.createdAt.desc())
                .fetch();
    }

    @Override
    public List<JoyGetInfo> getSavedAlbumJoys(Long albumId, Long userId) {

//        Boolean isJoySavedValue = true;  // 고정된 값(true) 설정

        return queryFactory
                .select(Projections.constructor(JoyGetInfo.class,
                        joy.joyId,
                        joy.joyIconNum,
                        joy.contents,
                        Expressions.asBoolean(true)))
//                        new Expression<>(Boolean.class)))
                .from(joy)
                .join(savingJoy)
                .on(savingJoy.joy.joyId.eq(joy.joyId)
                        .and(savingJoy.album.albumId.eq(albumId)))
                .orderBy(savingJoy.createdAt.desc())
                .fetch();
    }

    @Override
    public Boolean getIsAlbumSaved(Long albumId, Long userId) {
        long count = queryFactory
                .selectFrom(savingAlbum)
                .where(savingAlbum.album.albumId.eq(albumId)
                        .and(savingAlbum.user.userId.eq(userId)))
                .fetchCount();

        return count > 0L;
    }

    @Override
    public List<AlbumGetMyAllResponse> getMyAlbumsInfo(Long userId) {
        return queryFactory
                .select(Projections.constructor(AlbumGetMyAllResponse.class,
                        album.albumId,
                        album.albumInfo.albumIconNum,
                        album.albumInfo.albumColorNum,
                        album.name,
                        album.modifiedAt))
                .from(album)
                .where(album.user.userId.eq(userId))
                .fetch();
    }

    @Override
    public List<AlbumGetMyAllResponse> getMyBookmarksInfo(Long userId) {
        return queryFactory
                .select(Projections.constructor(AlbumGetMyAllResponse.class,
                        album.albumId,
                        album.albumInfo.albumIconNum,
                        album.albumInfo.albumColorNum,
                        album.name,
                        savingAlbum.createdAt))
                .from(savingAlbum)
                .join(savingAlbum.album, album)
                .where(savingAlbum.user.userId.eq(userId))
                .fetch();
    }

    @Override
    public List<AlbumGetCreatedResponse> getMyAlbumsCreated(Long userId) {
        return queryFactory
                .select(Projections.constructor(AlbumGetCreatedResponse.class,
                        album.albumId,
                        album.name))
                .from(album)
                .where(album.user.userId.eq(userId))
                .orderBy(album.createdAt.desc())
                .fetch();
    }

    @Override
    public List<AlbumGetJoyAllResponse> getMyJoyAllAlbums(Long joyId, Long userId) {
        return queryFactory
                .select(Projections.constructor(AlbumGetJoyAllResponse.class,
                        JPAExpressions
                                .selectOne()
                                .from(savingJoy)
                                .where(savingJoy.album.albumId.eq(album.albumId)
                                        .and(savingJoy.joy.joyId.eq(joyId)))
                                .exists(),
                        album.albumId,
                        album.albumInfo.albumIconNum,
                        album.albumInfo.albumColorNum,
                        album.name,
                        album.modifiedAt))
                .from(album)
                .where(album.user.userId.eq(userId))
                .fetch();
    }

    @Override
    public List<Long> getSavedMyAlbums(Long joyId, Long userId) {
        return queryFactory
                .select(album.albumId)
                .from(savingJoy)
                .join(savingJoy.album, album)
                .where(savingJoy.joy.joyId.eq(joyId)
                        .and(album.user.userId.eq(userId)))
                .fetch();
    }

    @Override
    public List<AlbumGetAllResponse> getAllIsOfficialAlbums() {

        return queryFactory
                .select(Projections.constructor(AlbumGetAllResponse.class,
                        album.albumId,
                        album.albumInfo.albumIconNum,
                        album.albumInfo.albumColorNum,
                        album.name))
                .from(album)
                .where(album.albumStatus.isOfficial.eq(true))
                .orderBy(album.albumId.desc())
                .fetch();
    }

    @Override
    public List<AlbumGetAllResponse> getAllAlbums(Long cursorId, int size) {
        BooleanExpression predicate = (cursorId == null) ? null : album.albumId.lt(cursorId);

        return queryFactory
                .select(Projections.constructor(AlbumGetAllResponse.class,
                        album.albumId,
                        album.albumInfo.albumIconNum,
                        album.albumInfo.albumColorNum,
                        album.name))
                .from(album)
                .where(album.albumStatus.isOfficial.eq(true).and(predicate))
                .orderBy(album.albumId.desc())
                .limit(size + 1)
                .fetch();
    }

    @Override
    public List<AlbumGetOthersResponse> getOtherAlbums(Long albumId, Long userId) {

        List<Long> albumIds = queryFactory
                .select(album.albumId)
                .from(album)
                .where(album.albumId.ne(albumId)
                        .and(album.user.userId.ne(userId))
                        .and(album.albumStatus.isOfficial.eq(true)))
                .fetch();

        Collections.shuffle(albumIds);
        List<Long> randomIds = albumIds.stream().limit(4).collect(Collectors.toList());

        return queryFactory
                .select(Projections.constructor(AlbumGetOthersResponse.class,
                        album.albumId,
                        album.albumInfo.albumIconNum,
                        album.albumInfo.albumColorNum,
                        album.name))
                .from(album)
                .where(album.albumId.in(randomIds))
                .fetch();
    }
}
