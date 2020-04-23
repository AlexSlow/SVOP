package com.svop.service.dailySchedule;

import com.svop.other.HeadProcessing.PageFormatter;
import com.svop.tables.daily_schedule.FlightSchedule;
import com.svop.tables.daily_schedule.Shift;
import com.svop.tables.daily_schedule.ShiftRepository;
import com.svop.tables.daily_schedule.ShiftStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalTime;
import java.util.Optional;

@Service
@Transactional
public class ShiftService {
    private static final Logger logger= LoggerFactory.getLogger(ShiftService.class);
    @Autowired
   private ShiftRepository shiftRepository;
    public Page<Shift> findAll(PageFormatter pageFormatter, Pageable pageable)
    {
        Page<Shift> page=shiftRepository.findAll(pageable);
        pageFormatter.setSize(page.getTotalPages());
        return page;
    }
    public boolean checkShift()
    {
        //logger.info("Начало  проверки смены графиков полета");
        Optional<Shift> shift=shiftRepository.findByDay(new Date(System.currentTimeMillis()));
        if (shift.isPresent()) {
            logger.info("смена -" + shift.get().toString());
            if (shift.get().getStatus()== ShiftStatus.Close) return false;
            else return true;
        }else{
            open();
            return true;
        }
    }
    private void open()
    {
        Shift nowShift=new Shift(SecurityContextHolder.getContext().getAuthentication().getName(),new Date(System.currentTimeMillis()), ShiftStatus.Open);
        nowShift.setTimeOpened( LocalTime.now () );
        shiftRepository.save(nowShift);
    }
    public void close()
    {
        Optional<Shift> shift=shiftRepository.findByDay(new Date(System.currentTimeMillis()));
        if (shift.isPresent()) {
            logger.info(" закрытие смены -" + shift.get().toString());
            if (shift.get().getStatus()!= ShiftStatus.Close)
            {
                shift.get().setStatus(ShiftStatus.Close);
                shift.get().setTimeClosed( LocalTime.now () );
                shiftRepository.save(shift.get());
            }
        }

    }

}
