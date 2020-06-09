package com.svop.service.control;

import com.svop.service.dailySchedule.FlightSheduleDaoService;
import com.svop.service.dailySchedule.StoicDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
@Service
public class InformaitionTabloControl extends AbstractTabloControl {
    @Autowired
    private MessageSource messageSource; //Бундлы
    @Autowired private StoicDaoService stoicDaoService;


    @Override
    public List<?> getScheduleLanguageViews() {
        return stoicDaoService.getInformationAboutStoics(PageRequest.of(page,page_size),locales[countries]);
    }



    public InformaitionTabloControl(@Autowired  @Qualifier("DailyTablo") ThreadPoolTaskScheduler taskScheduler) {
        super(taskScheduler, "/topic/TabloInfo");
    }

    @Override
    protected Map<String,String> getHeader()
    {
        Locale locale=locales[countries];
        Map headers=new HashMap();
        headers.put("day",messageSource.getMessage("daily.day",null,locale));
        headers.put("rout",messageSource.getMessage("reysy.rout",null,locale));
        headers.put("nomer",messageSource.getMessage("nomer_reys.nomer",null,locale));
        headers.put("nomerStoic",messageSource.getMessage("reception",null,locale));
        headers.put("time_otpravl",messageSource.getMessage("reysy.time_otpravl",null,locale));
        headers.put("status",messageSource.getMessage("flightSchedule.type",null,locale));
        headers.put("tablo_head",messageSource.getMessage("flightShedule.tablo.info",null,locale));
        headers.put("currentTime",messageSource.getMessage("other.current_time",null,locale));
        headers.put("locale",locale.getLanguage().toLowerCase());
        return headers;
    }
}
