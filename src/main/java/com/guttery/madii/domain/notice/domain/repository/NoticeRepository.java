package com.guttery.madii.domain.notice.domain.repository;

import com.guttery.madii.domain.notice.domain.model.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long>, NoticeQuerydslRepository {
}
