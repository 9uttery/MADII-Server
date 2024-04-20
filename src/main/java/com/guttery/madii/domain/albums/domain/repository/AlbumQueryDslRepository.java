package com.guttery.madii.domain.albums.domain.repository;

import com.guttery.madii.domain.albums.application.dto.*;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface AlbumQueryDslRepository {
    List<AlbumCreateResponse> getMyAlbums(Long userId);

    List<JoyGetInfo> getMyAlbumJoys(Long albumId);

    List<JoyGetInfo> getAlbumJoys(Long albumId, Long userId);

    Boolean getIsAlbumSaved(Long albumId, Long userId);

    List<AlbumGetMyAllResponse> getMyAlbumsInfo(Long userId);

    List<AlbumGetMyAllResponse> getMyBookmarksInfo(Long userId);

    List<AlbumGetCreatedResponse> getMyAlbumsCreated(Long userId);

    List<AlbumGetJoyAllResponse> getMyJoyAllAlbums(Long joyId, Long userId);

    List<Long> getSavedMyAlbums(Long joyId, Long userId);

    List<AlbumGetAllResponse> getAllIsOfficialAlbums();

    List<AlbumGetAllResponse> getAllAlbums(Long cursorId, int size);

    List<AlbumGetOthersResponse> getOtherAlbums(Long albumId, Long userId);

    List<JoyGetInfo> getSavedAlbumJoys(Long albumId, Long userId);
}
