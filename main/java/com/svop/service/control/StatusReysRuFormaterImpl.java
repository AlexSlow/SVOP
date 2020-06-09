package com.svop.service.control;

import com.svop.tables.daily_schedule.FlightSheduleStatus;

public class StatusReysRuFormaterImpl implements StatusReysFormater {
    @Override
    public String format(FlightSheduleStatus flightSheduleStatus) {
        switch (flightSheduleStatus)
        {
            case Неизмененный:
                return flightSheduleStatus.name();
            case Перемещенный:
                return flightSheduleStatus.name();
            case Отменен:
                return flightSheduleStatus.name();
            default: return flightSheduleStatus.name();
        }
    }
}
