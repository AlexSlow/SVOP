package com.svop.service.control;

import com.svop.tables.Handbooks.TypeReys;
import com.svop.tables.daily_schedule.FlightSheduleStatus;

import java.util.Locale;

public interface StatusReysFormater {
   String format(FlightSheduleStatus flightSheduleStatus);
}
