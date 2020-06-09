package com.svop.service.control;

import com.svop.tables.daily_schedule.FlightSheduleStatus;

public class StatusReysEnFormater implements StatusReysFormater {
    @Override
    public String format(FlightSheduleStatus flightSheduleStatus) {
        switch (flightSheduleStatus)
        {
            case Неизмененный:
                return "NotChangeble";
            case Перемещенный:
                return "Moveadle";
            case Отменен:
                return "Refuseble";
            default: return "Unknown";
        }
    }
}
