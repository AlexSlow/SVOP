package com.svop.service.dailySchedule;

import com.svop.View.DailyScheduleViews.DailySheduleView;
import com.svop.other.HeadProcessing.PageFormatter;
import com.svop.service.handbooks.ReysyService;
import com.svop.service.handbooks.RoutesService;
import com.svop.service.secutity.UserService;
import com.svop.tables.Handbooks.ReysyStatus;
import com.svop.tables.Handbooks.Routes;
import com.svop.tables.daily_schedule.Daily;
import com.svop.tables.daily_schedule.DailyRepository;
import com.svop.tables.temp.TempReysyRepository;
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
    public  List<DailySheduleView> getPageList(PageFormatter pageFormatter, Pageable pageable)
    {
        Page<Daily> dailyPage=dailyRepository.findAllByDayAfter(pageable,new Date(System.currentTimeMillis()));
        pageFormatter.setSize(dailyPage.getTotalPages());
        //Преобразуем для отображения
        List<DailySheduleView> dailySheduleViews=new ArrayList<>(dailyPage.getSize());
        for(Daily daily:dailyPage)
        {
            dailySheduleViews.add(getView(daily));
        }
        return dailySheduleViews;
    }
    public  List<DailySheduleView> getDailyViewBetwinPeriod(Date begin,Date end)
    {
        List<Daily> dailyPage=dailyRepository.findDailiesByDayBetween(begin,end);
        //Преобразуем для отображения
        List<DailySheduleView> dailySheduleViews=new ArrayList<>(dailyPage.size());
        for(Daily daily:dailyPage)
        {
            dailySheduleViews.add(getView(daily));
        }
        return dailySheduleViews;
    }
    public  List<Daily> getDailyBetweenPeriod(Date begin,Date end)
    {
        List<Daily> dailyPage=dailyRepository.findDailiesByDayBetween(begin,end);
        return dailyPage;
    }
    public  List<Daily> getActualDailyBetweenPeriod(Date begin,Date end)
    {
        List<Daily> dailyPage=dailyRepository.findDailiesByDayBetweenAndIzmenOmenNot(begin,end, ReysyStatus.Canceled);
        return dailyPage;
    }

    /**
     * Получть представление рейса
     * @param daily
     * @return
     */
    private DailySheduleView getView(Daily daily)
    {
        DailySheduleView dailySheduleView=new DailySheduleView();
        dailySheduleView.setRout(routesService.getRouts(daily.getRout().getName()));
        dailySheduleView.setNomer(daily.getNomer().getNomer());
        dailySheduleView.setType(daily.getType().name());
        dailySheduleView.setIzmen_otmen(daily.getIzmenOmen().name());
        String loc=userService.getLocale(SecurityContextHolder.getContext().getAuthentication().getName());
        if (loc==null)loc="ru";
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, new Locale(loc));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm", new Locale(loc));
        dailySheduleView.setDay(df.format(daily.getDay()));

        dailySheduleView.setTimeDeporture(daily.getTimeDeporture().format(dateTimeFormatter));
        dailySheduleView.setTimePrilet(daily.getTimePrilet().format(dateTimeFormatter));
        dailySheduleView.setTipVs(daily.getTipVs());
        dailySheduleView.setAirline(daily.getAirline().name());
        dailySheduleView.setStatus(daily.getIzmenOmen().name());
        dailySheduleView.setDirection(daily.getDirection().name());
        return dailySheduleView;
    }
}
