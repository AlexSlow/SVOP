package com.svop.service.dailySchedule;

import com.svop.View.DailyScheduleViews.FlightScheduleView;
import com.svop.other.HeadProcessing.PageFormatter;
import com.svop.service.handbooks.RoutesService;
import com.svop.service.secutity.UserService;
import com.svop.tables.daily_schedule.Daily;
import com.svop.tables.daily_schedule.FlightSheduleRepository;
import com.svop.tables.daily_schedule.FlightSchedule;
import com.svop.tables.daily_schedule.FlightSheduleStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.text.DateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Transactional
public class FlightSheduleDaoService {
    private static final Logger logger= LoggerFactory.getLogger(FlightSheduleDaoService.class);
    @Autowired private RoutesService routesService;
    @Autowired private UserService userService;
    @Autowired private FlightSheduleRepository flightSheduleRepository;

    /**
     * ПОлучить страницу
     * @param pageFormatter
     * @param pageable
     * @return
     */
    public List<FlightScheduleView> getPage(PageFormatter pageFormatter,Pageable pageable)
    {
        logger.info("Начало получения страницы рейсов");
        Page<FlightSchedule> page=flightSheduleRepository.findAll(pageable);
        List<FlightScheduleView> flightScheduleViewList =new ArrayList<>(page.getSize());
        for(FlightSchedule flightSchedule:page)
        {
            flightScheduleViewList.add(getView(flightSchedule));
        }
        pageFormatter.setSize(page.getTotalPages());
        logger.info("Конец получения страницы рейсов");
        return flightScheduleViewList;
    }

    /**
     * Преобразовать в представление
     * @param flightSchedule
     * @return
     */
    private FlightScheduleView getView(FlightSchedule flightSchedule)
    {
        FlightScheduleView flightScheduleView=new FlightScheduleView();
        flightScheduleView.setId(flightSchedule.getId());
        flightScheduleView.setRout(routesService.getRouts(flightSchedule.getDaily().getRout().getName()));

        flightScheduleView.setNomer(flightSchedule.getDaily().getNomer().getNomer());
        flightScheduleView.setType(flightSchedule.getDaily().getType().name());
        String loc=userService.getLocale(SecurityContextHolder.getContext().getAuthentication().getName());
        if (loc==null)loc="ru";
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, new Locale(loc));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm", new Locale(loc));
        flightScheduleView.setDay(df.format(flightSchedule.getDay()));

        flightScheduleView.setTimeDeporture(flightSchedule.getTimeDeporture().format(dateTimeFormatter));
        flightScheduleView.setTimePrilet(flightSchedule.getTimePrilet().format(dateTimeFormatter));
        flightScheduleView.setTipVs(flightSchedule.getDaily().getTipVs());
        flightScheduleView.setComment(flightSchedule.getComment());
        flightScheduleView.setStatus(flightSchedule.getStatus().name());
        flightScheduleView.setDirection(flightSchedule.getDaily().getDirection().name());

        return flightScheduleView;
    }

    /**
     * Удалить
     * @param id_list
     */
    public void delete(List<Integer> id_list)
    {
        logger.info("Начало удаление  графика полетов  "+id_list);
        flightSheduleRepository.deleteByIdIn(id_list);
        logger.info("Завершение даления ");
    }

    /**
     * ПОлучить выборку по дню
     * @param day
     * @return
     */
    public List<FlightScheduleView> getByDay(Date day)
    {
        logger.info("Начало получения графика полетов на день "+day);
        //Получим все дни, когда по основному дню
       List<FlightSchedule> flightScheduleList= flightSheduleRepository.findByDay(day);
       List<FlightScheduleView> flightScheduleViewList =new ArrayList<>(flightScheduleList.size());
        for(FlightSchedule flightSchedule:flightScheduleList)
        {
            flightScheduleViewList.add(getView(flightSchedule));
        }
        logger.info("Завершение получения  графика полетов на день "+day);
        return flightScheduleViewList;
    }

    /**
     *
     * @param day
     * @return
     */
    public List<FlightSchedule> getFlightShedulesByDay(Date day)
    {
        logger.info("Начало получения  графика полетов на день "+day);
        //Получим все дни, когда по основному дню
        List<FlightSchedule> flightScheduleList= flightSheduleRepository.findByDay(day);
        logger.info("Завершение получения  графика полетов на день "+day);
        return flightScheduleList;
    }

    public List<FlightScheduleView> getFlightShedulesViewById(List<Integer> idl)
    {
        logger.info("Начало получения  графика полетов по id "+idl);
        //Получим все дни, когда по основному дню
        List<FlightSchedule> flightScheduleList= flightSheduleRepository.findAllById(idl);
        List<FlightScheduleView> flightScheduleViewList =new ArrayList<>(flightScheduleList.size());


        for(FlightSchedule flightSchedule:flightScheduleList)
        {
            flightScheduleViewList.add(getView(flightSchedule));
        }
        logger.info("Завершение получения  списка");
        return flightScheduleViewList;
    }
    public List<FlightSchedule> getFlightShedulesById(List<Integer> idl)
    {
        logger.info("Начало получения  графика полетов по id "+idl);
        //Получим все дни, когда по основному дню
        logger.info("Завершение получения  списка");
        return flightSheduleRepository.findAllById(idl);
    }

    /**
     * Сохранить
     * @param dailies
     */
//Сохранить ежедневное расписание
    public void saveDaily(List<Daily> dailies,Date date)
    {
       List<FlightSchedule> schedules=getFlightShedulesByDay(date);

        List<FlightSchedule> flightSchedules=new ArrayList<>(dailies.size());
        //Проверка на наличие в графике такой же записи
        for(Daily daily:dailies)
        {
            boolean find=false;
           for(FlightSchedule flightSchedule:schedules) {
               if (flightSchedule.getDaily().getId().equals(daily.getId())) find=true;
           }
        if(!find) flightSchedules.add(DailyIntoFlightShedule(daily));

        }
        logger.info("Начало сохранения  графика полетов "+flightSchedules);
        flightSheduleRepository.saveAll(flightSchedules);
    }

    /**
     * Преобразовать ежедневное расписание в график полетов
     * @param daily
     * @return
     */
    private FlightSchedule DailyIntoFlightShedule(Daily daily)
    {
        FlightSchedule flightSchedule=new FlightSchedule();
        flightSchedule.setDaily(daily);
        flightSchedule.setDay(daily.getDay());
        flightSchedule.setStatus(FlightSheduleStatus.NotChanged);
        flightSchedule.setTimeDeporture(daily.getTimeDeporture());
        flightSchedule.setTimePrilet(daily.getTimePrilet());
        return flightSchedule;
    }
    public Optional<FlightSchedule> getFlightSheduleById(Integer id)
    {
        return flightSheduleRepository.findById(id);
    }
    public void saveAll(List<FlightSchedule> flightSchedules){
        flightSheduleRepository.saveAll(flightSchedules);
    }

}
