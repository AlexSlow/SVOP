package com.svop.controllers.API.daily;

import com.svop.View.DailyScheduleViews.DailyScheduleView;
import com.svop.service.dailySchedule.DailyDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value="/svop/api/daily",headers = {"Content-type=application/json"})
public class DailyRestController {
private @Autowired
    DailyDaoService dailyDaoService;
    @ResponseBody
    @RequestMapping(value="/getDailyReysByPeriod")
    public ResponseEntity<List<DailyScheduleView>> update(@RequestBody ArrayList<Date> dates) {

        return new ResponseEntity<List<DailyScheduleView>>(dailyDaoService.getDailyViewBetwinPeriod(dates.get(0),dates.get(1)),
                HttpStatus.OK);
    }
}
