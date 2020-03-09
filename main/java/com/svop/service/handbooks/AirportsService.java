package com.svop.service.handbooks;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.svop.other.JSON.ResponseParser;
import com.svop.tables.Handbooks.Airporty;
import com.svop.tables.Handbooks.AirportyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class AirportsService implements HandBooks {
    @Autowired
    AirportyRepo airportyRepo;
    @Autowired
    ResponseParser responseParser;


    @Override
    public String save(Object note) {
        Airporty aiporty=(Airporty)note;
      if(airportyRepo.save(aiporty)==null){
          return "success";
      }else
          return "failure";

    }

    @Override
    public String save(List<?> notes) {
        String isfailure="success";
            for (Object note : notes) {
                Airporty aiporty = (Airporty) note;

                if (airportyRepo.save(aiporty) == null) {
                 isfailure="failure";
                }
            }
        return isfailure;
    }

    @Override
    public String delete(List<?> notes) {
        String isfailure="success";
        for (Object note : notes) {
            Airporty aiporty = (Airporty) note;
            airportyRepo.delete(aiporty);
        }
        return isfailure;
    }

    @Override
    public String delete(Object note) {
        String isfailure="success";
        Airporty aiporty=(Airporty)note;
        airportyRepo.delete(aiporty);
        return isfailure;
    }
}
