package com.guttery.madii.domain.albums.infrastructure;

import com.guttery.madii.domain.albums.application.dto.AlbumCreateResponse;
import com.guttery.madii.domain.albums.domain.repository.AlbumQueryDslRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.guttery.madii.domain.albums.domain.model.QAlbum.album;

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
}
