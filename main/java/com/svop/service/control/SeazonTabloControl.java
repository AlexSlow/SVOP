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
public class SeazonTabloControl implements TabloControl {
    private boolean  isActive;
    //Время обновления
    @Value("${svop.time_refresh}") private Integer refresh_time;
    //Количество стран
    @Value("${svop.countries_amount}") private Integer countries_amount;
    private final  Integer page_size=10; //Размер страницы?
    private int totalPage=0; //Число страниц

    @Autowired
   private SeazonScheduleService seazonScheduleService;
    // Это пул потоков
    @Autowired
    @Qualifier("SeazonTablo")
    private ThreadPoolTaskScheduler taskScheduler;
    @Autowired
    private SimpMessageSendingOperations simpMessageSendingOperations; //Упаравление брокером сообщений
    @Autowired private MessageSource messageSource; //Бундлы
    //Триггер
    private PeriodicTrigger periodicTrigger;
    private ScheduledFuture scheduledFuture;
    //Указатель на страну и на страницу
    private int countries=0;
    private Integer page= 0;
    private List<SeazonScheduleLanguageView> seazonScheduleLanguageViews;
    private Locale[] locales={new Locale("ru"),new Locale("en"),new Locale("ch")};
//Класс задачи. Она на основании данных будет получать список табло
   public class SchedulePlanFormingTask implements Runnable{

        @Override
        public void run() {
            seazonScheduleLanguageViews=seazonScheduleService.getSeazonScheduleLanguageViews(PageRequest.of(page,page_size),countries);
            Map<String,Object> response=new HashMap<>();
            response.put("header",getHeader());
            response.put("body",seazonScheduleLanguageViews);
            simpMessageSendingOperations.convertAndSend("/topic/seazonTablo", response);
            //Тут мы делаем выборку
            //Если мы не прошли страны то
            if (countries<countries_amount-1)
            {
                countries++;
            }else
            {
                countries=0;

                if (page==totalPage-1)
                {
                    page=0;
                }else{
                    page++;
                }
            }

        }
    }

    public SeazonTabloControl()
    {

    }

    /**
     * Интерфейс работы с таблом. Активировать или выключить
     * @param isActive
     */
    @Override
    public void active(boolean isActive) {

        if (isActive)
        {
            if(this.isActive) return;
            //ПОлучим число страниц
            totalPage=0;
            totalPage=seazonScheduleService.getPageAmount(PageRequest.of(0,page_size));
            //System.out.println(totalPage);
            if (periodicTrigger==null) {
                periodicTrigger = new PeriodicTrigger(refresh_time, TimeUnit.SECONDS);
            }
                scheduledFuture= taskScheduler.schedule(new SchedulePlanFormingTask(),periodicTrigger);

        }else
        {
            //periodicTrigger=null;
            if (scheduledFuture!=null)
            {
                if (!scheduledFuture.isCancelled()){scheduledFuture.cancel(true);}
            }
        }
    this.isActive=isActive;
    }



    public SeazonTabloControl(Boolean isActive) {
        this.isActive = isActive;
    }

    public List<SeazonScheduleLanguageView> getSeazonScheduleLanguageViews() {

        return  seazonScheduleLanguageViews;
    }

    public void setSeazonScheduleLanguageViews(List<SeazonScheduleLanguageView> seazonScheduleLanguageViews) {
        this.seazonScheduleLanguageViews = seazonScheduleLanguageViews;
    }

    @Override
    public String toString() {
        return "SeazonTabloControl{" +
                "isActive=" + isActive +
                ", refresh_minutes=" + refresh_time +
                ", countries_amount=" + countries_amount +
                ", page_size=" + page_size +
                ", seazonScheduleService=" + seazonScheduleService +
                ", taskScheduler=" + taskScheduler +
                ", periodicTrigger=" + periodicTrigger +
                ", countries=" + countries +
                ", page=" + page +
                ", seazonScheduleLanguageViews=" + seazonScheduleLanguageViews +
                '}';
    }

    public Boolean isActive() {
        return isActive;
    }
public Map<String,String> getHeader()
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
    return headers;
}

}
