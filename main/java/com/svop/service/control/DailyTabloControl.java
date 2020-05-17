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
public class DailyTabloControl  implements TabloControl {

        private boolean  isActive;
        //Время обновления
        @Value("${svop.time_refresh}") private Integer refresh_time;
        //Количество стран
        @Value("${svop.countries_amount}") private Integer countries_amount;
        private final  Integer page_size=10; //Размер страницы?
        private int totalPage=0; //Число страниц

        @Autowired
        private FlightSheduleDaoService flightSheduleDaoService;
        // Это пул потоков
        @Autowired
        @Qualifier("DailyTablo")
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
        private List<FlightScheduleLanguageView> flightScheduleLanguageViews;
        private Locale[] locales={new Locale("ru"),new Locale("en"),new Locale("ch")};
        //Класс задачи. Она на основании данных будет получать список табло
        public class SchedulePlanFormingTask implements Runnable{
            @Override
            public void run() {
                flightScheduleLanguageViews=flightSheduleDaoService.getActualFlightScheduleLanguageList(PageRequest.of(page,page_size),countries,locales[countries]);
                Map<String,Object> response=new HashMap<>();
                response.put("header",getHeader());
                response.put("body",flightScheduleLanguageViews);
                simpMessageSendingOperations.convertAndSend("/topic/dailyTablo", response);
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

        public DailyTabloControl()
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
                totalPage=flightSheduleDaoService.getPageAmout(PageRequest.of(0,page_size));
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



        public DailyTabloControl(Boolean isActive) {
            this.isActive = isActive;
        }

    public List<FlightScheduleLanguageView> getFlightScheduleLanguageViews() {
        return flightScheduleLanguageViews;
    }

    public void setFlightScheduleLanguageViews(List<FlightScheduleLanguageView> flightScheduleLanguageViews) {
        this.flightScheduleLanguageViews = flightScheduleLanguageViews;
    }

    public Boolean isActive() {
            return isActive;
        }
        public Map<String,String> getHeader()
        {
            Locale locale=locales[countries];
            Map headers=new HashMap();
            headers.put("day",messageSource.getMessage("daily.day",null,locale));
            headers.put("rout",messageSource.getMessage("reysy.rout",null,locale));
            headers.put("nomer",messageSource.getMessage("nomer_reys.nomer",null,locale));
            headers.put("time_otpravl",messageSource.getMessage("reysy.time_otpravl",null,locale));
            headers.put("time_prib",messageSource.getMessage("reysy.time_prib",null,locale));
            return headers;
        }



}
