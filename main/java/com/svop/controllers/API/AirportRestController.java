package com.svop.controllers.API;

import com.svop.tables.Handbooks.AirportyRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


//Нужно будет доделать!
@RestController
@RequestMapping("svop/api/airports")
public class AirportRestController {
    @Autowired
    private AirportyRepo AirportsRepositiry;

    @RequestMapping(value="/update",produces= MediaType.APPLICATION_JSON_VALUE)
    public String update(@RequestParam(name="id",required = true)String id,
                         @RequestParam(name="NameRu",required = true)String NameRu,
                         @RequestParam(name="NameEng",required = false)String NameEng,
                         @RequestParam(name="NameCh",required = false)String NameCh,
                         @RequestParam(name="GMT",required = false)String GMT,
                         @RequestParam(name="ICAO",required = false)String ICAO,
                         @RequestParam(name="IATA",required = false)String IATA,
                         Model model) {

       int success=AirportsRepositiry.updateAirport(Integer.parseInt(id),NameRu,NameEng,NameCh,GMT,ICAO,IATA);
        return "{response :"+Integer.toString(success)+"}";

    }
    @RequestMapping(value="/test")
    public String test(@RequestParam(name="json",required = false)String id,Model model)
    {
        System.out.println("there test");
        return "{response :1}";
    }
}
