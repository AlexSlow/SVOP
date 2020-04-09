package com.svop.service.control;

import com.svop.View.SeazonScheduleViews.SeazonScheduleLanguageView;
import com.svop.service.SeazonSchedule.SeazonScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


@Service
public class SeazonTabloControl implements TabloControl {
    private Boolean  isActive;
    //Время обновления
    @Value("${svop.time_refresh}") private Integer refresh_minutes;
    //Количество стран
    @Value("${svop.countries_amount}") private Integer countries_amount;
    private final  Integer page_size=10;
    private Integer amount_of_page;

    @Autowired
   private SeazonScheduleService seazonScheduleService;

    // Это пул потоков
    @Autowired
    @Qualifier("SeazonTablo")
    private ThreadPoolTaskScheduler taskScheduler;
    //Триггер
    private PeriodicTrigger periodicTrigger;
    //Указатель на страну и на страницу
    private int countries=0;
    private Integer page= 0;
    private List<SeazonScheduleLanguageView> seazonScheduleLanguageViews;
//Класс задачи. Она на основании данных будет получать список табло
   public class SchedulePlanFormingTask implements Runnable{

        @Override
        public void run() {

            seazonScheduleLanguageViews=Collections.synchronizedList(seazonScheduleService.getSeazonScheduleLanguageViews(PageRequest.of(page,page_size),countries));
            //Тут мы делаем выборку
            //System.out.println("Номер страны  "+countries+" Страница  "+page);
            //System.out.println(seazonScheduleLanguageViews);
            //Если мы не прошли страны то
            if (countries<countries_amount-1)
            {
                countries++;
            }else
            {
                countries=0;

                if (page==page_size)
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
            if (periodicTrigger==null) {
                periodicTrigger = new PeriodicTrigger(refresh_minutes, TimeUnit.MINUTES);
               ScheduledFuture scheduledFuture= taskScheduler.schedule(new SchedulePlanFormingTask(),periodicTrigger);
            }
        }else
        {
            periodicTrigger=null;
            taskScheduler.shutdown();
        }
    this.isActive=isActive;
    }



    public SeazonTabloControl(Boolean isActive) {
        this.isActive = isActive;
    }

    public List<SeazonScheduleLanguageView> getSeazonScheduleLanguageViews() {
        return seazonScheduleLanguageViews;
    }

    public void setSeazonScheduleLanguageViews(List<SeazonScheduleLanguageView> seazonScheduleLanguageViews) {
        this.seazonScheduleLanguageViews = seazonScheduleLanguageViews;
    }

    @Override
    public String toString() {
        return "SeazonTabloControl{" +
                "isActive=" + isActive +
                ", refresh_minutes=" + refresh_minutes +
                ", countries_amount=" + countries_amount +
                ", page_size=" + page_size +
                ", amount_of_page=" + amount_of_page +
                ", seazonScheduleService=" + seazonScheduleService +
                ", taskScheduler=" + taskScheduler +
                ", periodicTrigger=" + periodicTrigger +
                ", countries=" + countries +
                ", page=" + page +
                ", seazonScheduleLanguageViews=" + seazonScheduleLanguageViews +
                '}';
    }
}
