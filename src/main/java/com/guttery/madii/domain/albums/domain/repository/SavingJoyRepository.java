package com.guttery.madii.domain.albums.domain.repository;

import com.guttery.madii.domain.albums.domain.model.Album;
import com.guttery.madii.domain.albums.domain.model.SavingJoy;
import com.guttery.madii.domain.joy.domain.model.Joy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SavingJoyRepository extends JpaRepository<SavingJoy, Long> {

    Optional<SavingJoy> findByJoyAndAlbum(Joy joy, Album album);

    void deleteByAlbumAlbumId(Long albumId);

    Optional<SavingJoy> findTopByAlbumOrderByJoyOrderDesc(Album album);

    void deleteAllByJoyJoyIdIn(List<Long> deletedJoyIds);

}
