package com.svop.controllers.API.daily;

import com.svop.View.DailyScheduleViews.FlightScheduleView;
import com.svop.View.DailyScheduleViews.StoicBindDto;
import com.svop.View.DailyScheduleViews.StoicDto;
import com.svop.View.DailyScheduleViews.StoicsAndFlightSheduleDto;
import com.svop.exeptions.SvopDataBaseExeption;
import com.svop.message.Success;
import com.svop.service.dailySchedule.FlightSheduleDaoService;
import com.svop.service.dailySchedule.FlightSheduleService;
import com.svop.service.dailySchedule.StoicDaoService;
import com.svop.tables.daily_schedule.Stoic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
@RestController
@RequestMapping(value="/svop/api/stoic",headers = {"Content-type=application/json"})
public class StoicRestController {
        private @Autowired
        StoicDaoService stoicDaoService;
        @ResponseBody
        @RequestMapping(value="/")
        public ResponseEntity save(@RequestBody List<Stoic> stoics) {
        stoicDaoService.saveWithoutFlightSchedule(stoics);
            return new ResponseEntity<Success>(new Success(),
                    HttpStatus.OK);
        }
    @ResponseBody
    @RequestMapping(value="getStoicsReys")
    public ResponseEntity getReys(@RequestBody Integer id) {
        try {
            List<StoicsAndFlightSheduleDto> list= stoicDaoService.getFlightSheduleByStoics(id);
            return new ResponseEntity<>(list,
                    HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity("Ошибка",
                    HttpStatus.OK);
        }
    }

    @ResponseBody
    @RequestMapping(value="bindStoicReys")
    public ResponseEntity bind(@RequestBody StoicBindDto stoicBindDto) {
        try {
           stoicDaoService.bind(stoicBindDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if ((stoicBindDto.getStoicId()!=null)&&(stoicBindDto.getStoicId().size()==1))
            return getReys(stoicBindDto.getStoicId().get(0));
        return getReys(-1);
    }

    @ResponseBody
    @RequestMapping(value="getStoics")
    public ResponseEntity<List<?>> getStoics() {
        return new ResponseEntity<>(stoicDaoService.findDtoAll(),HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value="fire")
    public ResponseEntity<List<?>> fire(@RequestBody List<Integer> id) {
            stoicDaoService.fire(id);
        return getStoics();
    }

    @ResponseBody
    @RequestMapping(value="getStatus")
    public StoicDto getStatus(@RequestBody Integer id) {

        StoicDto stoicDto=stoicDaoService.getStoic(id);
        return stoicDto;
    }

    @ResponseBody
    @DeleteMapping(value="/")
    public ResponseEntity delete(@RequestBody List<Integer> id) {
        try {
            stoicDaoService.delete(id);
            return new ResponseEntity("Success",HttpStatus.OK);
        }catch (Exception ex)
        {
            throw new SvopDataBaseExeption(ex.getLocalizedMessage());
        }
    }

}
