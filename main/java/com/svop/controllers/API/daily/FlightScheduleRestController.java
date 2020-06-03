package com.svop.controllers.API.daily;

import com.svop.View.DailyScheduleViews.FlightScheduleView;
import com.svop.exeptions.SvopExeption;
import com.svop.exeptions.response.SvopMessage;
import com.svop.message.Success;
import com.svop.message.flightShedule.FlightScheduleMove;
import com.svop.service.dailySchedule.FlightSheduleDaoService;
import com.svop.service.dailySchedule.FlightSheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping(value="/svop/api/flightShedule",headers = {"Content-type=application/json"})
public class FlightScheduleRestController {
    private @Autowired
    FlightSheduleDaoService flightSheduleDaoService;
    private @Autowired
    FlightSheduleService flightSheduleService;

    @ResponseBody
    @RequestMapping(value="/getByDay")
    public ResponseEntity<List<FlightScheduleView>> getByDay(@RequestBody Date date) {

        return new ResponseEntity<List<FlightScheduleView>>(flightSheduleDaoService.getByDay(date),
                HttpStatus.OK);
    }
    @ResponseBody
    @RequestMapping(value="/build")
    public ResponseEntity<List<FlightScheduleView>> build(@RequestBody Date date) {
        return new ResponseEntity<List<FlightScheduleView>>(flightSheduleService.build(date),
                HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value="/move")
    public ResponseEntity<SvopMessage> move(@RequestBody  List<FlightScheduleMove> moves) {


        try {
            flightSheduleService.move(moves);
        }catch (Exception e)
        {
            e.printStackTrace();
            throw new SvopExeption(e.getLocalizedMessage());
        }
        return new ResponseEntity<>(new Success("Успех"),
                HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value="/cancel")
    public ResponseEntity<SvopMessage> cancel(@RequestBody  List<Integer> id_list) {

        flightSheduleService.cancel(id_list);
        return new ResponseEntity<>(new Success("Успех"),
                HttpStatus.OK);
    }
    @ResponseBody
    @RequestMapping(value="/uncancel")
    public ResponseEntity<SvopMessage> uncancel(@RequestBody  List<Integer> id_list) {

        flightSheduleService.uncancel(id_list);
        return new ResponseEntity<>(new Success("Успех"),
                HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value="/getById")
    public ResponseEntity<List<FlightScheduleView>> getById(@RequestBody List<Integer> idl) {

        if (idl==null) return new ResponseEntity<>(null,HttpStatus.OK);
        return new ResponseEntity<>(flightSheduleDaoService.getFlightShedulesViewById(idl),
                HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value="/closeShift")
    public ResponseEntity<SvopMessage> close() {

            flightSheduleService.closeShift();
        return new ResponseEntity<>(new Success("Смена закрыта "),
                HttpStatus.OK);
    }



}
