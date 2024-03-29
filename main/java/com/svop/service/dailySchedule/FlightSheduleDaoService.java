package com.svop.service.dailySchedule;

import com.svop.View.DailyScheduleViews.FlightScheduleLanguageView;
import com.svop.View.DailyScheduleViews.FlightScheduleView;
import com.svop.other.HeadProcessing.PageFormatter;
import com.svop.service.control.DailyTabloStatusReysFactory;
import com.svop.service.control.StatusFactory;
import com.svop.service.control.StatusReysFormater;
import com.svop.service.handbooks.RoutesService;
import com.svop.service.secutity.UserService;
import com.svop.tables.daily_schedule.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.text.DateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class FlightSheduleDaoService implements FlightSheduleDaoServiceInterface {
    private static final Logger logger= LoggerFactory.getLogger(FlightSheduleDaoService.class);
    @Autowired private RoutesService routesService;
    @Autowired private UserService userService;
    @Autowired private FlightSheduleRepository flightSheduleRepository;
    @Autowired private FlightScheduleStatusManagerInt flightScheduleStatusManagerInt;

    /**
     * Получить локализованное представление
     * @param pageable
     * @return
     */
    @Override
    @Cacheable(cacheNames = "fightScheduledLanguage")
    public List<FlightScheduleLanguageView> getActualFlightScheduleLanguageList(Pageable pageable,Integer country,Locale locale)
    {
        Page<FlightSchedule>  flightScheduleList=flightSheduleRepository.findByDayAndStatusOrderByDay(pageable,new Date(System.currentTimeMillis()),FlightSheduleStatus.Неизмененный);
        List<FlightScheduleLanguageView> languageViews=new ArrayList<>(flightScheduleList.getSize());
        StatusReysFormater statusReysFormater=new DailyTabloStatusReysFactory().getFormater(locale);

        for(FlightSchedule flightSchedule:flightScheduleList)
        {
            FlightScheduleLanguageView flightScheduleLanguageView=new FlightScheduleLanguageView();
            flightScheduleLanguageView.setRout(routesService.getRoutsByNomer(country,flightSchedule.getDaily().getRout().getName()));
            DateFormat df=DateFormat.getDateInstance(DateFormat.SHORT,locale);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm",locale);
            flightScheduleLanguageView.setDay(df.format(flightSchedule.getDay()));
            flightScheduleLanguageView.setTimeDeporture(formatter.format(flightSchedule.getTimeDeporture()));
            flightScheduleLanguageView.setTimePrilet(formatter.format(flightSchedule.getTimePrilet()));
            flightScheduleLanguageView.setNomer(flightSchedule.getDaily().getNomer().getNomer());
            flightScheduleLanguageView.setStatus(statusReysFormater.format(flightSchedule.getStatus()));
            flightScheduleLanguageView.setLogo(flightSchedule.getDaily().getNomer().getAircompany().getLogo());
            languageViews.add(flightScheduleLanguageView);
        }
        return languageViews;

    }
    @Override
    public int getPageAmout(Pageable pageable)
    {
        Page<FlightSchedule> page=flightSheduleRepository.findAll(pageable);
        return  page.getTotalPages();
    }
    /**
     * ПОлучить страницу
     * @param pageFormatter
     * @param pageable
     * @return
     */
    @Override
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
        flightScheduleView.setTipVs(flightSchedule.getVs());

        if(flightSchedule.getFlightScheduleNext()!=null)
        {
            flightScheduleView.setTimeDeportureNext(flightSchedule.getFlightScheduleNext().getTimeDeporture().format(dateTimeFormatter));
            flightScheduleView.setTimePriletNext(flightSchedule.getFlightScheduleNext().getTimePrilet().format(dateTimeFormatter));
            flightScheduleView.setDayNext(df.format(flightSchedule.getFlightScheduleNext().getDay()));
            flightScheduleView.setDateNextMills(flightSchedule.getFlightScheduleNext().getDay().getTime());
        }
        flightScheduleView.setMoveable(flightScheduleStatusManagerInt.isMoveAvalible(flightSchedule));

        return flightScheduleView;
    }

    /**
     * Удалить
     * @param id_list
     */
    @Override
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
    @Override
    public List<FlightScheduleView> getByDay(Date day)
    {
        logger.info("Начало получения графика полетов на день "+day);
        //Получим все дни, когда по основному дню
       List<FlightSchedule> flightScheduleList= flightSheduleRepository.findByDayOrderByDay(day);
       List<FlightScheduleView> flightScheduleViewList =new ArrayList<>(flightScheduleList.size());
        for(FlightSchedule flightSchedule:flightScheduleList)
        {
            flightScheduleViewList.add(getView(flightSchedule));
        }
        logger.info("Завершение получения  графика полетов на день "+day);
        return flightScheduleViewList;
    }

    @Override
    public List<FlightScheduleView> getActulaByDay(Date day)
    {
        logger.info("Начало получения графика полетов на день "+day);
        //Получим все дни, когда по основному дню
        List<FlightSchedule> flightScheduleList= flightSheduleRepository.findByDayAndStatusOrderByDay(day,FlightSheduleStatus.Неизмененный);
        List<FlightScheduleView> flightScheduleViewList =new ArrayList<>(flightScheduleList.size());
        for(FlightSchedule flightSchedule:flightScheduleList)
        {
            flightScheduleViewList.add(getView(flightSchedule));
        }
        logger.info("Завершение получения  графика полетов на день "+day);
        return flightScheduleViewList;
    }

    /**
     * Для стоек регистрации
     * @param day
     * @return
     */
    @Override
    public List<FlightScheduleView> getActulaViletReysByDay(Date day) {
        List<FlightSchedule> flightScheduleList= flightSheduleRepository.findByDayAndStatusAndDaily_DirectionOrderByDay(day,FlightSheduleStatus.Неизмененный, DailyDirection.Вылет);
        List<FlightScheduleView> flightScheduleViewList =new ArrayList<>(flightScheduleList.size());
        for(FlightSchedule flightSchedule:flightScheduleList)
        {
            flightScheduleViewList.add(getView(flightSchedule));
        }
        return flightScheduleViewList;
    }

    /**
     *
     * @param day
     * @return
     */
    @Override
    public List<FlightSchedule> getFlightShedulesByDay(Date day)
    {
        logger.info("Начало получения  графика полетов на день "+day);
        //Получим все дни, когда по основному дню
        List<FlightSchedule> flightScheduleList= flightSheduleRepository.findByDayOrderByDay(day);
        logger.info("Завершение получения  графика полетов на день "+day);
        return flightScheduleList;
    }
    @Override
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
    @Override
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
    @Override
    public void saveDaily(List<Daily> dailies,Date date)
    {
        dailies.forEach(d->System.out.println(d.getId()+" "+d.getDay()));
        List<FlightSchedule> flightSchedulesforSave=new ArrayList<>();
        //Проверка на наличие в графике такой же записи
        List<Integer> id_condidats=dailies.stream().map(Daily::getId).collect(Collectors.toList());
        List<FlightSchedule> flightSchedules=flightSheduleRepository.findAllByDailyIdIsIn(id_condidats);
        flightSchedules.forEach(d->System.out.println(d.getId()));
        for(Daily daily:dailies)
        {
            boolean find=false;
           for(FlightSchedule flightSchedule:flightSchedules) {

               if (flightSchedule.getDaily().getId().equals(daily.getId())) find=true;
           }
        if(!find) {flightSchedules.add(DailyIntoFlightShedule(daily));};

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
        flightSchedule.setStatus(FlightSheduleStatus.Неизмененный);
        flightSchedule.setTimeDeporture(daily.getTimeDeporture());
        flightSchedule.setTimePrilet(daily.getTimePrilet());
        flightSchedule.setVs(daily.getTipVs());
        return flightSchedule;
    }
    @Override
    public Optional<FlightSchedule> getFlightSheduleById(Integer id)
    {
        return flightSheduleRepository.findById(id);
    }
    @Override
    public void saveAll(List<FlightSchedule> flightSchedules){
        flightSheduleRepository.saveAll(flightSchedules);
    }
    @Override
    public  void deleteList(List<FlightSchedule> flightSchedules){
        flightSheduleRepository.deleteAll(flightSchedules);
    }

    @Override
    public List<FlightScheduleView> findBetweenPeriod(Date begin, Date end) {
        List<FlightSchedule> flightSchedules=flightSheduleRepository.findFlightSchedulesByDayBetweenOrderByDay(begin,end);
        List<FlightScheduleView> flightScheduleViewList=new ArrayList<>();
        flightSchedules.forEach(item->flightScheduleViewList.add(getView(item)));
        return flightScheduleViewList;
    }
}
