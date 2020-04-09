package com.svop.controllers.API;

import com.svop.exeptions.SvopDataBaseExeption;
import com.svop.exeptions.response.SvopMessage;
import com.svop.message.Success;
import com.svop.service.handbooks.AirportsService;
import com.svop.tables.Handbooks.Airporty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping(value="/svop/api/airports",headers = {"Content-type=application/json"})
public class AirportRestController {
    @Autowired
    private AirportsService airportsService;

    @ResponseBody
    @RequestMapping(value="/save")
    public ResponseEntity<SvopMessage> update(@RequestBody ArrayList<Airporty> airporty) {
        try {
            airportsService.save(airporty);
        }catch (DataIntegrityViolationException  ex)
        {
            throw new SvopDataBaseExeption(ex.getLocalizedMessage());
        }
        return new ResponseEntity<SvopMessage>(new Success("Success"),HttpStatus.OK);

    }



}
