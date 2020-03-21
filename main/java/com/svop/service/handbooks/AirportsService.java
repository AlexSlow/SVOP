package com.svop.service.handbooks;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.svop.tables.Handbooks.Airporty;
import com.svop.tables.Handbooks.AirportyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class AirportsService  {
    @Autowired
    AirportyRepo airportyRepo;

    public ResponseEntity<String> save(Object note) {
        Airporty aiporty=(Airporty)note;
      if(airportyRepo.save(aiporty)==null){
          return new ResponseEntity<String>("Сохранение завершено", HttpStatus.OK);
      }else
          return new ResponseEntity<String>("Сохраненить не удалось", HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<String> save(List<?> notes) {
        ResponseEntity<String> response= new ResponseEntity<>("Сохранение произошло успешно",HttpStatus.OK);
            for (Object note : notes) {
                Airporty aiporty = (Airporty) note;

                if (airportyRepo.save(aiporty) == null) {
                    response=new ResponseEntity<>("Сохранение произошло с ошибкой", HttpStatus.BAD_REQUEST);
                }
            }
        return response;
    }

    public ResponseEntity<String> delete(List<?> notes) {
        ResponseEntity<String> response= new ResponseEntity<>("Сохранение произошло успешно",HttpStatus.OK);
        for (Object note : notes) {
            Airporty aiporty = (Airporty) note;
            airportyRepo.delete(aiporty);
        }
        return response;
    }

    public ResponseEntity<String> delete(Object note) {
        ResponseEntity<String> response= new ResponseEntity<>("Сохранение произошло успешно",HttpStatus.OK);
        Airporty aiporty=(Airporty)note;
        airportyRepo.delete(aiporty);
        return response;
    }
}
