package com.svop.service.dailySchedule;

import com.svop.tables.daily_schedule.FlightSchedule;
import com.svop.tables.daily_schedule.FlightSheduleStatus;
import org.springframework.stereotype.Component;

@Component
public class FlightScheduleStatusManager implements FlightScheduleStatusManagerInt {
    //Можно ли перемещать
    @Override
    public boolean isMoveAvalible(FlightSchedule flightSchedule) {
        if (flightSchedule.getStatus() == FlightSheduleStatus.Перемещенный) {
            FlightSchedule next = flightSchedule.getFlightScheduleNext();
            if (next != null) {
                if (next.getFlightScheduleNext() != null) {
                    return false;
                } else {
                    return true;
                }
            } else return true;
        } else if (flightSchedule.getStatus() == FlightSheduleStatus.Отменен) return false;
        else return true;
    }
}
