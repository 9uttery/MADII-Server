package com.guttery.madii.domain.albums.domain.repository;

import com.guttery.madii.domain.albums.domain.model.Album;
import com.guttery.madii.domain.albums.domain.model.SavingJoy;
import com.guttery.madii.domain.joy.domain.model.Joy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingJoyRepository extends JpaRepository<SavingJoy, Long> {

    SavingJoy findByJoyAndAlbum(Joy joy, Album album);

    void deleteByAlbumAlbumId(Long albumId);
}
