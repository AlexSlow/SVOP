package com.svop.service.dailySchedule;

import com.svop.View.DailyScheduleViews.DailyScheduleView;
import com.svop.other.HeadProcessing.PageFormatter;
import com.svop.service.handbooks.RoutesService;
import com.svop.service.secutity.UserService;
import com.svop.tables.Handbooks.ReysyStatus;
import com.svop.tables.daily_schedule.Daily;
import com.svop.tables.daily_schedule.DailyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.DateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class DailyDaoService {
    @Autowired
    private UserService userService;
    @Autowired
    private DailyRepository dailyRepository;
    @Autowired
    private RoutesService routesService;
    private static final Logger logger= LoggerFactory.getLogger(DailySheduleService.class.getName());
    public  List<DailyScheduleView> getPageList(PageFormatter pageFormatter, Pageable pageable)
    {
        Page<Daily> dailyPage=dailyRepository.findAllByDayAfter(pageable,new Date(System.currentTimeMillis()));
        pageFormatter.setSize(dailyPage.getTotalPages());
        //Преобразуем для отображения
        List<DailyScheduleView> dailyScheduleViews =new ArrayList<>(dailyPage.getSize());
        for(Daily daily:dailyPage)
        {
            dailyScheduleViews.add(getView(daily));
        }
        return dailyScheduleViews;
    }
    public  List<DailyScheduleView> getDailyViewBetwinPeriod(Date begin, Date end)
    {
        List<Daily> dailyPage=dailyRepository.findDailiesByDayBetweenOrderByDay(begin,end);
        //Преобразуем для отображения
        List<DailyScheduleView> dailyScheduleViews =new ArrayList<>(dailyPage.size());
        for(Daily daily:dailyPage)
        {
            dailyScheduleViews.add(getView(daily));
        }
        return dailyScheduleViews;
    }
    public  List<Daily> getDailyBetweenPeriod(Date begin,Date end)
    {
        List<Daily> dailyPage=dailyRepository.findDailiesByDayBetweenOrderByDay(begin,end);
        return dailyPage;
    }
    public  List<Daily> getActualDailyBetweenPeriod(Date begin,Date end)
    {
        List<Daily> dailyPage=dailyRepository.findDailiesByDayBetweenAndIzmenOmenNotOrderByDay(begin,end, ReysyStatus.Изменен);
        return dailyPage;
    }

    /**
     * Получть представление рейса
     * @param daily
     * @return
     */
    private DailyScheduleView getView(Daily daily)
    {
        DailyScheduleView dailyScheduleView =new DailyScheduleView();
        dailyScheduleView.setRout(routesService.getRouts(daily.getRout().getName()));
        dailyScheduleView.setNomer(daily.getNomer().getNomer());
        dailyScheduleView.setType(daily.getType().name());
        dailyScheduleView.setIzmen_otmen(daily.getIzmenOmen().name());
        String loc=userService.getLocale(SecurityContextHolder.getContext().getAuthentication().getName());
        if (loc==null)loc="ru";
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, new Locale(loc));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm", new Locale(loc));
        dailyScheduleView.setDay(df.format(daily.getDay()));

        dailyScheduleView.setTimeDeporture(daily.getTimeDeporture().format(dateTimeFormatter));
        dailyScheduleView.setTimePrilet(daily.getTimePrilet().format(dateTimeFormatter));
        dailyScheduleView.setTipVs(daily.getTipVs());
        dailyScheduleView.setAirline(daily.getAirline().name());
        dailyScheduleView.setStatus(daily.getIzmenOmen().name());
        dailyScheduleView.setDirection(daily.getDirection().name());
        return dailyScheduleView;
    }
}
