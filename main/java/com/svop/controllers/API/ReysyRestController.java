package com.svop.controllers.API;

import com.svop.View.ReysyListView;
import com.svop.View.message.FlightRequest;
import com.svop.service.handbooks.ReysyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/svop/api/reysy/",headers = {"Content-type=application/json"})
public class ReysyRestController {
    @Autowired
    private ReysyService reysyService;
    @ResponseBody
    @RequestMapping(value="/get_reys_from_period")
    public List<ReysyListView> getReys(@RequestBody FlightRequest flightRequest)
    {
        //System.out.println(flightRequest);
        return reysyService.getReysListByType(flightRequest.getType(),flightRequest.getSezon_selected());

    }
}
