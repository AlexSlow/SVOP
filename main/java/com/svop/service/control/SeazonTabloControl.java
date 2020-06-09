package com.svop.service.control;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.svop.View.SeazonScheduleViews.SeazonScheduleLanguageView;
import com.svop.service.SeazonSchedule.SeazonScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Класс сервис управления содержимым табло
 */

@Service
public class SeazonTabloControl extends AbstractTabloControl {

    @Autowired
   private SeazonScheduleService seazonScheduleService;
    // Это пул потоков
    @Autowired private MessageSource messageSource; //Бундлы


    @Override
    public List<?> getScheduleLanguageViews() {
        return seazonScheduleService.getSeazonScheduleLanguageViews(PageRequest.of(page,page_size),countries);
    }
    public SeazonTabloControl(@Autowired  @Qualifier("SeazonTablo") ThreadPoolTaskScheduler taskScheduler) {
        super(taskScheduler, "/topic/seazonTablo");
    }
    @Override
    public String toString() {
        return super.toString();
    }

        @Override
        protected Map<String,String> getHeader()
{
    Locale locale=locales[countries];
    Map headers=new HashMap();
    headers.put("aircompanies",messageSource.getMessage("aircompanies.title",null,locale));
    headers.put("period_start",messageSource.getMessage("reysy.period_start",null,locale));
    headers.put("period_end",messageSource.getMessage("reysy.period_end",null,locale));
    headers.put("rout",messageSource.getMessage("reysy.rout",null,locale));
    headers.put("prilet",messageSource.getMessage("reysy.prilet",null,locale));
    headers.put("vilet",messageSource.getMessage("reysy.vilet",null,locale));
    headers.put("logo",messageSource.getMessage("aircompanies.logo",null,locale));
    headers.put("nomer_reys",messageSource.getMessage("reysy.nomer_reys",null,locale));
    headers.put("prilet_days",messageSource.getMessage("reysy.prilet_days",null,locale));
    headers.put("time_otpravl",messageSource.getMessage("reysy.time_otpravl",null,locale));

    headers.put("time_prib",messageSource.getMessage("reysy.time_prib",null,locale));
    headers.put("vilet_days",messageSource.getMessage("reysy.vilet_days",null,locale));
    headers.put("tablo_head",messageSource.getMessage("sezon.tablo.head",null,locale));
    headers.put("currentTime",messageSource.getMessage("other.current_time",null,locale));
    headers.put("locale",locale.getLanguage().toLowerCase());
    return headers;
}

}
