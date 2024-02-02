package com.guttery.madii.domain.albums.domain.repository;

import com.guttery.madii.domain.albums.application.dto.AlbumCreateResponse;
import com.guttery.madii.domain.albums.application.dto.AlbumGetJoyAllResponse;
import com.guttery.madii.domain.albums.application.dto.AlbumGetMyAllResponse;
import com.guttery.madii.domain.albums.application.dto.JoyGetInfo;

import java.util.List;

public interface AlbumQueryDslRepository {
    List<AlbumCreateResponse> getMyAlbums(Long userId);

    List<JoyGetInfo> getMyAlbumJoys(Long albumId);

    List<JoyGetInfo> getAlbumJoys(Long albumId, Long userId);

    Boolean getIsAlbumSaved(Long albumId, Long userId);

    List<AlbumGetMyAllResponse> getMyAlbumsInfo(Long userId);

    List<AlbumGetMyAllResponse> getMyBookmarksInfo(Long userId);

    List<AlbumGetJoyAllResponse> getMyJoyAllAlbums(Long joyId, Long userId);
}
