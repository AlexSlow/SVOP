package com.svop.service.dailySchedule;

import com.svop.View.DailyScheduleViews.StoicBindDto;
import com.svop.View.DailyScheduleViews.StoicDto;
import com.svop.View.DailyScheduleViews.StoicsAndFlightSheduleDto;
import com.svop.tables.daily_schedule.FlightSchedule;
import com.svop.tables.daily_schedule.Stoic;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface StoicDaoInterface {
    public List<StoicDto> getStoicsDtoList(@NotNull List<Stoic> stoics);
    public List<Stoic> findAll();
    public List<StoicDto> findDtoAll();
    public List<StoicDto> getStoicsDtoListByIdList(@NotNull List<Integer> idList);
    public  List<Stoic> stoicListFactory(List<StoicDto> stoicDtos);
    public void saveWithoutFlightSchedule(List<Stoic> stoics);
    List<StoicsAndFlightSheduleDto> getFlightSheduleByStoics(@NotNull Integer stoicId) throws Exception;
    void bind(@NotNull StoicBindDto stoicBindDto) throws Exception;
    public void fire(@NotNull List<Integer> idl);
    public StoicDto getStoic(@NotNull Integer id);
    void delete(List<Integer> id);

}
