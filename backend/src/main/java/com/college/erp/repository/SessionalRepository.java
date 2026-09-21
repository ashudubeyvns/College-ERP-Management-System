package com.college.erp.repository;

import com.college.erp.model.entity.Sessional;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SessionalRepository extends JpaRepository<Sessional, Long> {
    List<Sessional> findByRollNoOrderByIdDesc(String rollNo);
    List<Sessional> findAllByOrderByIdDesc();
}
