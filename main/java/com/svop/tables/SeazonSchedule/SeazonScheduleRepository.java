package com.svop.tables.SeazonSchedule;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface SeazonScheduleRepository extends JpaRepository<SeazonSchedule,Integer> {
    Page<SeazonSchedule> findAll(Pageable pageable);
    Optional<SeazonSchedule> findByImg(String img);
}
