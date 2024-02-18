package com.guttery.madii.domain.report.domain.repository;

import com.guttery.madii.domain.report.domain.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
