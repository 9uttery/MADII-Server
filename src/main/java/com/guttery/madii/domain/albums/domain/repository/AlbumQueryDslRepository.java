package com.guttery.madii.domain.albums.domain.repository;

import com.guttery.madii.domain.albums.application.dto.AlbumCreateResponse;

import java.util.List;

public interface AlbumQueryDslRepository {
    List<AlbumCreateResponse> getMyAlbums(Long userId);
}
