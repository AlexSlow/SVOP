package com.svop.tables.daily_schedule;

import jdk.nashorn.internal.runtime.regexp.joni.encoding.ObjPtr;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface FlightSheduleRepository extends JpaRepository<FlightSchedule,Integer> {
    @Override
    Page<FlightSchedule> findAll(Pageable pageable);
    Page<FlightSchedule> findAllByDay(Pageable pageable, Date date);
    List<FlightSchedule> findByDay(Date date);
    Page<FlightSchedule> findByDayAndStatus(Pageable pageable,Date date,FlightSheduleStatus status);
    List<FlightSchedule> findByDayAndStatus(Date date,FlightSheduleStatus status);
    void deleteByIdIn(List<Integer> ids);
}
