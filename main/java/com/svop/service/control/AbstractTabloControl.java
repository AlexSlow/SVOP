package com.svop.service.control;

import com.svop.View.SeazonScheduleViews.SeazonScheduleLanguageView;
import com.svop.service.SeazonSchedule.SeazonScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;

import java.util.*;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public abstract class AbstractTabloControl implements TabloControl {
    private boolean  isActive;
    //Время обновления
    @Value("${svop.time_refresh}") protected Integer refresh_time;
    //Количество стран
    @Value("${svop.countries_amount}") private Integer countries_amount;
    protected final  Integer page_size=10; //Размер страницы?
    protected int totalPage=0; //Число страниц

    @Autowired
    private SeazonScheduleService seazonScheduleService;
    // Это пул потоков
    private ThreadPoolTaskScheduler taskScheduler;
    @Autowired
    private SimpMessageSendingOperations simpMessageSendingOperations; //Упаравление брокером сообщений
    //Триггер
    private PeriodicTrigger periodicTrigger;
    private ScheduledFuture scheduledFuture;
    //Указатель на страну и на страницу
    protected volatile int countries=0;
    protected volatile Integer page= 0;
    private List<?> ScheduleLanguageViews;
    protected Locale[] locales={new Locale("ru"),new Locale("en"),new Locale("ch")};
    private Map<String,String> HeadersStore=new HashMap<>();
    public String topic;
    public Map<String, String> getHeadersStore() {
        return HeadersStore;
    }



    //Класс задачи. Она на основании данных будет получать список табло
    public class SchedulePlanFormingTask implements Runnable{

        @Override
        public void run() {
            ScheduleLanguageViews=getScheduleLanguageViews();
           // ScheduleLanguageViews=seazonScheduleService.getSeazonScheduleLanguageViews(PageRequest.of(page,page_size),countries);
            Map<String,Object> response=new HashMap<>();
            HeadersStore=getHeader();
            response.put("header",HeadersStore);
            response.put("body",ScheduleLanguageViews);

            simpMessageSendingOperations.convertAndSend(topic, response);
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

    public AbstractTabloControl(ThreadPoolTaskScheduler taskScheduler,String topic)
    {
    this.taskScheduler=taskScheduler;
    this.topic=topic;
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
            scheduledFuture= taskScheduler.schedule(new AbstractTabloControl.SchedulePlanFormingTask(),periodicTrigger);

        }else
        {
            //periodicTrigger=null;
            if (scheduledFuture!=null)
            {
                if (!scheduledFuture.isCancelled()){scheduledFuture.cancel(true);}
            }
            countries=0;
            page=0;
        }
        this.isActive=isActive;
    }



    public AbstractTabloControl(Boolean isActive) {
        this.isActive = isActive;
    }

    public abstract List<?> getScheduleLanguageViews();

    @Override
    public String toString() {
        return "AbstractTabloControl{" +
                "isActive=" + isActive +
                ", refresh_minutes=" + refresh_time +
                ", countries_amount=" + countries_amount +
                ", page_size=" + page_size +
                ", seazonScheduleService=" + seazonScheduleService +
                ", taskScheduler=" + taskScheduler +
                ", periodicTrigger=" + periodicTrigger +
                ", countries=" + countries +
                ", page=" + page +
                ", seazonScheduleLanguageViews=" + ScheduleLanguageViews +
                '}';
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Boolean isActive() {
        return isActive;
    }
    protected abstract Map<String,String> getHeader();
}
