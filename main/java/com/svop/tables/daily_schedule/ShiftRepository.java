package com.svop.tables.daily_schedule;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.Optional;

public interface ShiftRepository extends JpaRepository<Shift,Integer> {

    Optional<Shift> findByDay(Date day);
    Page<Shift> findAll(Pageable pageable);
}
