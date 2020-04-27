package com.svop.service.dailySchedule;

import com.svop.View.DailyScheduleViews.FlightScheduleView;
import com.svop.exeptions.SvopExeption;
import com.svop.message.flightShedule.FlightScheduleMove;
import com.svop.tables.daily_schedule.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FlightSheduleService {
    @Autowired private DailyDaoService dailyDaoService;
    @Autowired private FlightSheduleDaoService flightSheduleDaoService;
    @Autowired private ShiftService shiftService;
    private static final Logger logger= LoggerFactory.getLogger(FlightSheduleService.class);
    public List<FlightScheduleView> build()
    {
        logger.info("Начало  построения графиков полета");
        //Проверка открыта ли смена
        if (shiftService.checkShift()) {
            //Выберем из ежедневных расписаний за следующие сутки
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, 1);
            logger.info(calendar.toString());
            //Получить рейсы еж на следующие сутки те, что не были отменены
            List<Daily> dailies = dailyDaoService.getDailyBetweenPeriod(new Date(calendar.getTime().getTime()), new Date(calendar.getTime().getTime()));
           // System.out.println(dailies);

            //сохранить ежедневное расписание
            flightSheduleDaoService.saveDaily(dailies,new Date(calendar.getTime().getTime()));
            List<FlightScheduleView> flightSchedules=flightSheduleDaoService.getByDay(new Date(calendar.getTime().getTime()));
            logger.info("Конец  построения графиков полета");
            return flightSchedules;
        }else{
            //Если смена закрыта, тогда выходим
            return flightSheduleDaoService.getByDay(new Date(System.currentTimeMillis()));
        }


    }
    public void move(List<FlightScheduleMove> flightScheduleMoves) throws SvopExeption
    {
        //Проверка открыта ли смена
        if (shiftService.checkShift()) {
            logger.info("Начало  перемещения графика полета ");
            ArrayList<FlightSchedule> flightSchedules = new ArrayList<>(flightScheduleMoves.size() * 2);
            for (FlightScheduleMove flightScheduleMove : flightScheduleMoves) {
                Optional<FlightSchedule> flightSchedule = flightSheduleDaoService.getFlightSheduleById(flightScheduleMove.getId());
                if (!flightSchedule.isPresent()) throw new SvopExeption("svop.error.fs.notfound");
                if ((flightScheduleMove.getDay() == null) || (flightScheduleMove.getDeporture() == null) || (flightScheduleMove.getPrilet() == null))
                    throw new SvopExeption("svop.error.incorrect");

                //Созданим новый FH на новыую дату

                if (flightSchedule.get().getStatus() == FlightSheduleStatus.NotChanged) {
                    FlightSchedule flightScheduleNew = new FlightSchedule();
                    flightScheduleNew.setDay(flightScheduleMove.getDay());
                    flightScheduleNew.setTimeDeporture(flightScheduleMove.getDeporture());
                    flightScheduleNew.setTimePrilet(flightScheduleMove.getPrilet());
                    flightScheduleNew.setComment("");
                    //Определить предыдущий и следующий ФШ
                    flightScheduleNew.setFlightSchedulePrevious(flightSchedule.get());
                    flightSchedule.get().setFlightScheduleNext(flightScheduleNew);
                    flightScheduleNew.setStatus(FlightSheduleStatus.NotChanged);
                    flightScheduleNew.setDaily(flightSchedule.get().getDaily());
                    //Пометить как перемещенный
                    flightSchedule.get().setComment(flightScheduleMove.getComment());
                    flightSchedule.get().setStatus(FlightSheduleStatus.Moved);
                    flightSchedules.add(flightScheduleNew);
                    flightSchedules.add(flightSchedule.get());
                }
            }
            flightSheduleDaoService.saveAll(flightSchedules);
            logger.info("Конец  перемещения графика полета");
        }

    }

    public void cancel(List<Integer> idlist) throws SvopExeption {
        setStatus(idlist,FlightSheduleStatus.Cancaled);
    }
    public void uncancel(List<Integer> idlist) throws SvopExeption {
        setStatus(idlist,FlightSheduleStatus.NotChanged);

    }
    private void setStatus(List<Integer> idl,FlightSheduleStatus status)
    {
      List<FlightSchedule> flightSchedules=flightSheduleDaoService.getFlightShedulesById(idl);
        for(FlightSchedule flightSchedule:flightSchedules)
        {
           //Если перемещен, то тогда нельзя удалять или возвращать
            if (flightSchedule.getStatus()!=FlightSheduleStatus.Moved)
            {
                flightSchedule.setStatus(status);
              //  if (flightSchedule.)
            }
        }
        flightSheduleDaoService.saveAll(flightSchedules);
    }
    public void closeShift()
    {
    shiftService.close();
    }


}
