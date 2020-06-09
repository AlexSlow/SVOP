package com.svop.service.dailySchedule;

import com.svop.View.DailyScheduleViews.StoicBindDto;
import com.svop.View.DailyScheduleViews.StoicDto;
import com.svop.View.DailyScheduleViews.StoicLanguageDto;
import com.svop.View.DailyScheduleViews.StoicsAndFlightSheduleDto;
import com.svop.tables.daily_schedule.FlightSchedule;
import com.svop.tables.daily_schedule.Stoic;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Locale;

public interface StoicDaoInterface {
    public List<StoicDto> getStoicsDtoList(@NotNull Iterable<Stoic> stoics);
    public List<Stoic> findAll();
    public List<StoicDto> findDtoAll();
    public List<StoicDto> getStoicsDtoListByIdList(@NotNull Iterable<Integer> idList);
    public List<Stoic> stoicListFactory(Iterable<StoicDto> stoicDtos);
    public void saveWithoutFlightSchedule(Iterable<Stoic> stoics);
    List<StoicsAndFlightSheduleDto> getFlightSheduleByStoics(@NotNull Integer stoicId) throws Exception;
    void bind(@NotNull StoicBindDto stoicBindDto) throws Exception;
    public void fire(@NotNull Iterable<Integer> idl);
    public StoicDto getStoic(@NotNull Integer id);
    void delete(Iterable<Integer> id);
    public List<StoicLanguageDto> getInformationAboutStoics(Pageable pageable, Locale locale); //Получить все стойки и рейсы
    public List<StoicLanguageDto> stoicLanguageDtoListFactory(@NotNull Iterable<StoicDto> stoics,Locale locale);
    public StoicLanguageDto stoicLanguageDtoFactory(@NotNull StoicDto stoic,Locale locale);
}
