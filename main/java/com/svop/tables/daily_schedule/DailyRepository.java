package com.svop.tables.daily_schedule;

import com.svop.tables.Handbooks.NomerReys;
import com.svop.tables.Handbooks.Reysy;
import com.svop.tables.Handbooks.ReysyStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface DailyRepository   extends JpaRepository<Daily,Integer> {
Page<Daily> findAllByDayAfter(Pageable pageable,Date date);
Optional<Daily> findByReysAndDayAndDirection(Reysy reysy, Date day,DailyDirection direction);
List<Daily> findDailiesByDayBetween(Date start,Date end);
List<Daily> findDailiesByDayBetweenAndIzmenOmenNot(Date start,Date end,ReysyStatus reysyStatus);
}
