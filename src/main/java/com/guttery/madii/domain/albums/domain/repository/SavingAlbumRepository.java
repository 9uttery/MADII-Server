package com.guttery.madii.domain.albums.domain.repository;

import com.guttery.madii.domain.albums.domain.model.Album;
import com.guttery.madii.domain.albums.domain.model.SavingAlbum;
import com.guttery.madii.domain.user.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingAlbumRepository extends JpaRepository<SavingAlbum, Long> {
    Boolean existsByUserAndAlbum(User user, Album album);
}
