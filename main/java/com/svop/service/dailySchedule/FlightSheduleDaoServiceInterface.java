package com.svop.service.dailySchedule;

import com.svop.View.DailyScheduleViews.FlightScheduleLanguageView;
import com.svop.View.DailyScheduleViews.FlightScheduleView;
import com.svop.other.HeadProcessing.PageFormatter;
import com.svop.tables.daily_schedule.Daily;
import com.svop.tables.daily_schedule.FlightSchedule;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface FlightSheduleDaoServiceInterface {
    public List<FlightScheduleLanguageView> getActualFlightScheduleLanguageList(Pageable pageable, Integer country, Locale locale);
    public int getPageAmout(Pageable pageable);
    public List<FlightScheduleView> getPage(PageFormatter pageFormatter, Pageable pageable);
    public void delete(List<Integer> id_list);
    public List<FlightScheduleView> getByDay(Date day);
    List<FlightScheduleView> getActulaByDay(Date day);
    List<FlightScheduleView> getActulaViletReysByDay(Date day);
    public List<FlightSchedule> getFlightShedulesByDay(Date day);
    public List<FlightScheduleView> getFlightShedulesViewById(List<Integer> idl);
    public List<FlightSchedule> getFlightShedulesById(List<Integer> idl);
    public void saveDaily(List<Daily> dailies, Date date);
    public Optional<FlightSchedule> getFlightSheduleById(Integer id);
    public void saveAll(List<FlightSchedule> flightSchedules);
    void deleteList(List<FlightSchedule> flightSchedules);
    List<FlightScheduleView> findBetweenPeriod(Date begin,Date end);

}
