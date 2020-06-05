package com.svop.service.dailySchedule;

import com.svop.View.SeazonScheduleViews.SeazonScheduleView;
import com.svop.other.HeadProcessing.PageFormatter;
import com.svop.service.handbooks.ReysyService;
import com.svop.service.handbooks.RoutesService;
import com.svop.tables.Handbooks.Reysy;
import com.svop.tables.Handbooks.ReysyNomerType;
import com.svop.tables.Handbooks.ReysyStatus;
import com.svop.tables.SeazonSchedule.SeazonSchedule;
import com.svop.tables.daily_schedule.Daily;
import com.svop.tables.daily_schedule.DailyDirection;
import com.svop.tables.daily_schedule.DailyRepository;
import com.svop.tables.journal.ProcedureJournalService;
import com.svop.tables.journal.TypeProcedure;
import com.svop.tables.temp.TempReysy;
import com.svop.tables.temp.TempReysyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.*;

@Service
@Transactional
public class DailySheduleService {
    @Autowired
    DailyRepository dailyRepository;
    @Autowired
    private ReysyService reysyService;
    @Autowired
    TempReysyRepository tempReysyRepository;

    @Autowired @Qualifier("Daily")
    private ProcedureJournalService procedureJournalService;
    private List<Daily> savedCollection=new ArrayList<>();
    private static final Logger logger= LoggerFactory.getLogger(DailySheduleService.class.getName());
    public synchronized void forming()
    {
        logger.info("Начало формирования ежедневного расписания");
        savedCollection.clear();
        List<Reysy> reysyListRegular=reysyService.getAllActualReys(new Date(System.currentTimeMillis ()));
        //System.out.println(reysyListRegular);
        Calendar calendar=Calendar.getInstance();
        Calendar end=Calendar.getInstance();
        for(Reysy reys:reysyListRegular)
        {

            calendar.setTime(reys.getPeriod_start());
            end.setTime(reys.getPeriod_end());
            //Перебрать все дни
            while(calendar.before(end))
            {
                //Получить дни вылета и прилета в списках
                List<Integer> prilet_days= new ArrayList<>();
                String [] prilet_days_temp_str=reys.getPrilet_days().split("/");
                for(int i=0;i<prilet_days_temp_str.length;i++){
                    if (!prilet_days_temp_str[i].equals(""))
                    prilet_days.add(Integer.parseInt(prilet_days_temp_str[i]));}

                List<Integer> vilet_days= new ArrayList<>();
                String [] vilet_days_temp_str=reys.getVilet_days().split("/");
                for(int i=0;i<vilet_days_temp_str.length;i++){
                    if (!vilet_days_temp_str[i].equals(""))
                    vilet_days.add(Integer.parseInt(vilet_days_temp_str[i]));}
                logger.info("Дни выполнения "+prilet_days+"  "+vilet_days);

                //Проверка дня прилета
                if (checkWorkDay(prilet_days,calendar.get(Calendar.DAY_OF_WEEK)))
                {
                    save(calendar,reys, DailyDirection.Прилет);
                }if (checkWorkDay(vilet_days,calendar.get(Calendar.DAY_OF_WEEK)))
            {
                save(calendar,reys,DailyDirection.Вылет);
            }
            calendar.add(Calendar.DATE,1);
            }
            //Удалить отмененные дни
           Optional<TempReysy> tempReysy= tempReysyRepository.findByReysyId(reys.getId());
           if (tempReysy.isPresent())
           {
               logger.info("Удалить все удаленные дни");

               List<Integer> prilet_days_temp= new ArrayList<>();
               String [] prilet_days_temp_str_temp=tempReysy.get().getTempPriletDays().split("/");
               for(int i=0;i<prilet_days_temp_str_temp.length;i++){
                   if (!prilet_days_temp_str_temp[i].equals(""))
                   prilet_days_temp.add(Integer.parseInt(prilet_days_temp_str_temp[i]));
               }

               List<Integer> vilet_days_temp= new ArrayList<>();
               String [] vilet_days_temp_str_temp=tempReysy.get().getTempViletDays().split("/");
               for(int i=0;i<vilet_days_temp_str_temp.length;i++)
               {   if (!vilet_days_temp_str_temp[i].equals(""))
                   vilet_days_temp.add(Integer.parseInt(vilet_days_temp_str_temp[i]));}
               //Перебор таблицы daily
               ArrayList<Daily> deleteDaily=new ArrayList<>();
               for(Daily daily:dailyRepository.findAll())
               {
                   Calendar cal=GregorianCalendar.getInstance();
                   cal.setTime(daily.getDay());
                   if (daily.getDirection().equals(DailyDirection.Прилет))
                   {
                      if(checkWorkDay(prilet_days_temp,cal.get(Calendar.DAY_OF_WEEK)))
                      {
                          deleteDaily.add(daily);
                      }
                   }else{
                       if(checkWorkDay(vilet_days_temp,cal.get(Calendar.DAY_OF_WEEK)))
                       {
                           deleteDaily.add(daily);
                       }
                   }
               }
               //Удаление
               logger.info("Отмена "+deleteDaily);
            //   dailyRepository.deleteAll(deleteDaily);
               for (Daily daily:deleteDaily){
                   daily.setIzmenOmen(ReysyStatus.Отменен);
               }
               dailyRepository.saveAll(deleteDaily);


           }
        }
        tempReysyRepository.deleteAll();
        //Сохранение
        dailyRepository.saveAll(savedCollection);
        logger.info("Окончание формирования ежедневного расписания");
        procedureJournalService.save(SecurityContextHolder.getContext().getAuthentication().getName(),new Date(System.currentTimeMillis()),TypeProcedure.Ежедневное);
    }

    public void save(Calendar day, Reysy reysy, DailyDirection direction)
    {
        //Формирование
        Daily daily=new Daily();
        daily.setReys(reysy);
        daily.setAirline(reysy.getAirline());
        daily.setDay(new Date(day.getTimeInMillis()));
        daily.setIzmenOmen(reysy.getIzmen_otmen());
        daily.setRout(reysy.getRout());
        if (direction==DailyDirection.Прилет)
        {
            daily.setNomer(reysy.getNomer_prilet());
            daily.setTimeDeporture(reysy.getPrilet_time_otpravl());
            daily.setTimePrilet(reysy.getPrilet_time_prib());
        }

        if (direction==DailyDirection.Вылет)
        {
            daily.setNomer(reysy.getNomer_vilet());
            daily.setTimeDeporture(reysy.getVilet_time_otpravl());
            daily.setTimePrilet(reysy.getVilet_time_prib());
        }
        daily.setDirection(direction);
        daily.setTipVs(reysy.getTip_vs());
        daily.setType(reysy.getType());


    logger.info("Проверка на наличие изменений");
    System.out.println(reysy);
    System.out.println(new Date(day.getTimeInMillis()));
    System.out.println(direction);
       Optional<Daily> dailyOld=dailyRepository.findByReysAndDayAndDirection(reysy,new Date(day.getTimeInMillis()),direction);
        if (!dailyOld.isPresent())
        {
            logger.info("Запись отсутствует");
            //dailyRepository.save(daily);
            savedCollection.add(daily);

        }else{
            logger.info("Запись найдена");
            if (daily.equals(dailyOld.get()))
            {
                logger.info("Изменения не обнаружены");

            }else {
                System.out.println(daily);
                System.out.println(dailyOld.get());
                logger.info("Изменения обнаружены");
                daily.setId(dailyOld.get().getId());
                if (reysy.getIzmen_otmen()!=ReysyStatus.Отменен)
                daily.setIzmenOmen(ReysyStatus.Изменен);
                //dailyRepository.save(daily);
                savedCollection.add(daily);
            }
        }
        logger.info("Сохранено в коллекцию");

    }
    private boolean checkWorkDay(List<Integer> days,Integer day)
    {
        logger.info("Проверка дня недели "+days+" "+day);
        for(Integer item:days)
        {
            if (item.equals(day)) return true;
        }
        return false;
    }
}
