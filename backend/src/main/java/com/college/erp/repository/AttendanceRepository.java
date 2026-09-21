package com.college.erp.repository;

import com.college.erp.model.entity.AttendanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<AttendanceRecord, Long> {
    List<AttendanceRecord> findByRollNoOrderByDateDesc(String rollNo);
    List<AttendanceRecord> findAllByOrderByDateDesc();
    List<AttendanceRecord> findByDateAndSubjectCodeOrderByRollNoAsc(LocalDate date, String subjectCode);
    long countByStatus(String status);
    long countByRollNoAndStatus(String rollNo, String status);
}
