package com.guttery.madii.domain.albums.domain.repository;

import com.guttery.madii.domain.albums.domain.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Long> {
}
