package com.svop.controllers.API;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.svop.service.handbooks.AirportsService;
import com.svop.tables.Handbooks.Airporty;
import com.svop.tables.Handbooks.AirportyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;


//Нужно будет доделать!
@RestController
@RequestMapping(value="svop/api/airports",headers = {"Content-type=application/json"})
public class AirportRestController {
    @Autowired
    private AirportsService airportsService;

    @ResponseBody
    @RequestMapping(value="/save")
    public ResponseEntity<String> update(@RequestBody ArrayList<Airporty> airporty) {

        return airportsService.save(airporty);
    }
    @ResponseBody
    @RequestMapping(value="/delete")
    public  ResponseEntity<String> delete(@RequestBody ArrayList<Integer> airporty_id) {
        return  airportsService.delete(airporty_id);
    }


}
