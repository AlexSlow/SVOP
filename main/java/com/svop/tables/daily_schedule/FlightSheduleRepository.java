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
    Page<FlightSchedule> findAllByDayOrderByDay(Pageable pageable, Date date);
    List<FlightSchedule> findByDayOrderByDay(Date date);
    Page<FlightSchedule> findByDayAndStatusOrderByDay(Pageable pageable,Date date,FlightSheduleStatus status);
    List<FlightSchedule> findByDayAndStatusOrderByDay(Date date,FlightSheduleStatus status);
    List<FlightSchedule> findByDayAndStatusAndDaily_DirectionOrderByDay(Date date,FlightSheduleStatus status,DailyDirection direction);
    List<FlightSchedule> findAllByDailyIdIsIn(List<Integer> idl);
    void deleteByIdIn(List<Integer> ids);
    List<FlightSchedule> findFlightSchedulesByDayBetweenOrderByDay(Date start,Date end);
}
