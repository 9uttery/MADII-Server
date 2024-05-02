package com.guttery.madii.domain.albums.domain.repository;

import com.guttery.madii.domain.albums.domain.model.Album;
import com.guttery.madii.domain.user.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Long> {
    void deleteByUser(User user);
}
