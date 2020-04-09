package com.svop.controllers.API;

import com.svop.View.ReysyListView;
import com.svop.message.FlightRequest;
import com.svop.service.handbooks.ReysyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/svop/api/reysy/",headers = {"Content-type=application/json"})
public class ReysyRestController {
    private static Logger logger= LoggerFactory.getLogger(ReysyRestController.class);
    @Autowired
    private ReysyService reysyService;
    @ResponseBody
    @RequestMapping(value="/get_reys_from_period")
    public List<ReysyListView> getReys(@RequestBody FlightRequest flightRequest)
    {
        try {
            return reysyService.getReysListByType(flightRequest.getType(),flightRequest.getSezon_selected());
        }catch(Exception e)
        {
            e.printStackTrace();
            logger.error("Получить рейсы за период не удалось!");
        }
        return null;
    }
}
