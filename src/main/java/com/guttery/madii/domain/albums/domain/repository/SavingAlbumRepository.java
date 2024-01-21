package com.guttery.madii.domain.albums.domain.repository;

import com.guttery.madii.domain.albums.domain.model.SavingJoy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingAlbumRepository extends JpaRepository<SavingJoy, Long> {
}
