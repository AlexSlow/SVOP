package com.svop.controllers.API;

import com.svop.exeptions.SvopDataBaseExeption;
import com.svop.exeptions.response.SvopMessage;
import com.svop.message.Success;
import com.svop.service.handbooks.AirportsService;
import com.svop.tables.Handbooks.Airporty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sun.plugin.javascript.navig.Array;


import javax.annotation.PostConstruct;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value="/svop/api/airports",headers = {"Content-type=application/json"})
public class AirportRestController {
    @Autowired
    private AirportsService airportsService;

    @ResponseBody
    @PostMapping(value="/")
    public ResponseEntity<SvopMessage> update(@RequestBody ArrayList<Airporty> airporty) {
        try {
            airportsService.save(airporty);
        }catch (DataIntegrityViolationException  ex)
        {
            throw new SvopDataBaseExeption(ex.getLocalizedMessage());
        }
        return new ResponseEntity<SvopMessage>(new Success("Success"),HttpStatus.OK);

    }

    @ResponseBody
    @PostMapping(value="/get")
    public ResponseEntity<List<Airporty>> get(@RequestBody Map<String,Integer> page) {
        if (page==null){
            return new ResponseEntity(airportsService.getAll(),HttpStatus.OK);
        }

        try {
         Pageable pageable = PageRequest.of(page.get("page"),page.get("size"),Sort.by("nameRu").ascending());
          return new ResponseEntity(airportsService.getPage(pageable),HttpStatus.OK);
        }catch (DataIntegrityViolationException  ex)
        {
            ArrayList<Airporty> airporties=new ArrayList<>();
            throw new SvopDataBaseExeption(ex.getLocalizedMessage());
        }
    }

    @ResponseBody
    @DeleteMapping(value="/")
    public ResponseEntity delete(@RequestBody List<Integer> id) {
       try {
           airportsService.deleteById(id);
           return new ResponseEntity("Success",HttpStatus.OK);
       }catch (Exception ex)
       {
           throw new SvopDataBaseExeption(ex.getLocalizedMessage());
       }
    }



}
