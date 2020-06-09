package com.svop.service.control;

import com.svop.View.DailyScheduleViews.FlightScheduleLanguageView;
import com.svop.View.SeazonScheduleViews.SeazonScheduleLanguageView;
import com.svop.service.SeazonSchedule.SeazonScheduleService;
import com.svop.service.dailySchedule.FlightSheduleDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
@Service
public class DailyTabloControl  extends AbstractTabloControl {
    @Autowired private MessageSource messageSource; //Бундлы
    @Autowired private  FlightSheduleDaoService flightSheduleDaoService;


    @Override
    public List<?> getScheduleLanguageViews() {
        return flightSheduleDaoService.getActualFlightScheduleLanguageList(PageRequest.of(page,page_size),countries,locales[countries]);
    }

    public DailyTabloControl(@Autowired  @Qualifier("DailyTablo") ThreadPoolTaskScheduler taskScheduler) {
        super(taskScheduler, "/topic/dailyTablo");
    }

    @Override
        protected Map<String,String> getHeader()
        {
            Locale locale=locales[countries];
            Map headers=new HashMap();
            headers.put("day",messageSource.getMessage("daily.day",null,locale));
            headers.put("rout",messageSource.getMessage("reysy.rout",null,locale));
            headers.put("nomer",messageSource.getMessage("nomer_reys.nomer",null,locale));
            headers.put("time_otpravl",messageSource.getMessage("reysy.time_otpravl",null,locale));
            headers.put("time_prib",messageSource.getMessage("reysy.time_prib",null,locale));
            headers.put("status",messageSource.getMessage("flightSchedule.type",null,locale));
            headers.put("tablo_head",messageSource.getMessage("flightShedule.tablo_daily",null,locale));
            headers.put("currentTime",messageSource.getMessage("other.current_time",null,locale));
            headers.put("locale",locale.getLanguage().toLowerCase());
            return headers;
        }



}
