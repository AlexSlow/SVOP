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
    @Autowired
    private DailyDaoService dailyDaoService;
    @Autowired
    private FlightSheduleDaoService flightSheduleDaoService;
    @Autowired
    private ShiftService shiftService;
    @Autowired private FlightScheduleStatusManagerInt flightScheduleStatusManagerInt;
    private static final Logger logger = LoggerFactory.getLogger(FlightSheduleService.class);

    /**
     * Задача метода - выбрать нужную коллекцию за сутки
     * @return
     */
    public List<FlightScheduleView> build(Date date) {
        logger.info("Начало  построения графиков полета");
        //Проверка открыта ли смена
        if (shiftService.checkShift()) {
            //Выберем из ежедневных расписаний за следующие сутки
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DATE, 1);
            logger.info(calendar.toString());
            //Получить рейсы еж на следующие сутки те, что не были отменены
            List<Daily> dailies = dailyDaoService.getDailyBetweenPeriod(date, new Date(calendar.getTime().getTime()));
            // System.out.println(dailies);
            //сохранить ежедневное расписание
            flightSheduleDaoService.saveDaily(dailies,date);
            List<FlightScheduleView> flightSchedules = flightSheduleDaoService.getByDay(date);
            logger.info("Конец  построения графиков полета");
            return flightSchedules;
        } else {
            //Если смена закрыта, тогда выходим
            return flightSheduleDaoService.getByDay(new Date(System.currentTimeMillis()));
        }


    }

    public void move(List<FlightScheduleMove> flightScheduleMoves) throws SvopExeption {
        //Проверка открыта ли смена
        // if (shiftService.checkShift()) {
        logger.info("Начало  перемещения графика полета ");
        ArrayList<FlightSchedule> flightSchedules = new ArrayList<>(flightScheduleMoves.size() * 2);
        List<FlightSchedule> deleteList=new ArrayList<>();
        for (FlightScheduleMove flightScheduleMove : flightScheduleMoves) {
            Optional<FlightSchedule> flightSchedule = flightSheduleDaoService.getFlightSheduleById(flightScheduleMove.getId());
            if (!flightSchedule.isPresent()) throw new SvopExeption("svop.error.fs.notfound");
           // if ((flightScheduleMove.getDay() == null) || (flightScheduleMove.getDeporture() == null) || (flightScheduleMove.getPrilet() == null))
            //      throw new SvopExeption("svop.error.incorrect");

            //Созданим новый FH на новыую дату
            if (!flightScheduleMove.isUndoMoving()) {
                if (flightSchedule.get().getStatus() == FlightSheduleStatus.Неизмененный) {
                    FlightSchedule flightScheduleNew = new FlightSchedule();
                    flightScheduleNew.setDay(flightScheduleMove.getDay()!=null? flightScheduleMove.getDay():flightSchedule.get().getDay());
                    flightScheduleNew.setTimeDeporture(flightScheduleMove.getDeporture()!=null? flightScheduleMove.getDeporture() : flightSchedule.get().getTimeDeporture());
                    flightScheduleNew.setTimePrilet(flightScheduleMove.getPrilet()!=null? flightScheduleMove.getPrilet() : flightSchedule.get().getTimePrilet());
                    flightScheduleNew.setComment("");
                    flightScheduleNew.setVs(flightScheduleMove.getVs()!=null? flightScheduleMove.getVs() : flightSchedule.get().getVs());
                    //Определить предыдущий и следующий ФШ
                    flightScheduleNew.setFlightSchedulePrevious(flightSchedule.get());
                    flightSchedule.get().setFlightScheduleNext(flightScheduleNew);
                    flightScheduleNew.setStatus(FlightSheduleStatus.Неизмененный);
                    flightScheduleNew.setDaily(flightSchedule.get().getDaily());
                    //Пометить как перемещенный
                    flightSchedule.get().setComment(flightScheduleMove.getComment());
                    flightSchedule.get().setStatus(FlightSheduleStatus.Перемещенный);
                    flightSchedules.add(flightScheduleNew);
                    flightSchedules.add(flightSchedule.get());
                } else if (flightSchedule.get().getStatus() == FlightSheduleStatus.Перемещенный) {
                    //Редактирование существующего
                    FlightSchedule flightScheduleMoveOld = flightSchedule.get().getFlightScheduleNext();
                    flightScheduleMoveOld.setDay(flightScheduleMove.getDay());
                    flightScheduleMoveOld.setTimeDeporture(flightScheduleMove.getDeporture());
                    flightScheduleMoveOld.setTimePrilet(flightScheduleMove.getPrilet());
                    flightSchedule.get().setComment(flightScheduleMove.getComment());
                    flightSchedules.add(flightScheduleMoveOld);
                    flightSchedules.add(flightSchedule.get());
                }
            }else
            {
                //Если требуется отмена перемещения, тогда
                deleteList.add(flightSchedule.get().getFlightScheduleNext());
                flightSchedule.get().setStatus(FlightSheduleStatus.Неизмененный);
                flightSchedule.get().setComment("");
                flightSchedule.get().setFlightScheduleNext(null);
                flightSchedules.add(flightSchedule.get());
            }
        }
        flightSheduleDaoService.saveAll(flightSchedules);
        flightSheduleDaoService.deleteList(deleteList);
        logger.info("Конец  перемещения графика полета");
        //}

    }

    public void cancel(List<Integer> idlist) throws SvopExeption {
        setStatus(idlist, FlightSheduleStatus.Отменен);
    }

    public void uncancel(List<Integer> idlist) throws SvopExeption {
        setStatus(idlist, FlightSheduleStatus.Неизмененный);

    }

    private void setStatus(List<Integer> idl, FlightSheduleStatus status) {
        List<FlightSchedule> flightSchedules = flightSheduleDaoService.getFlightShedulesById(idl);
        for (FlightSchedule flightSchedule : flightSchedules) {
            //Если перемещен, то тогда нельзя удалять или возвращать

            if (flightSchedule.getStatus()!=FlightSheduleStatus.Перемещенный) {
                flightSchedule.setStatus(status);
                //  if (flightSchedule.)
            }
        }
        flightSheduleDaoService.saveAll(flightSchedules);
    }

    public void closeShift() {
        shiftService.close();
    }
}
