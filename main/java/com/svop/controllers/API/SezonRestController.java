package com.svop.controllers.API;

import com.svop.exeptions.response.SvopMessage;
import com.svop.message.Success;
import com.svop.tables.Handbooks.Sezon;
import com.svop.tables.Handbooks.SezonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value="/svop/api/sezons",headers = {"Content-type=application/json"})
public class SezonRestController {
    @Autowired
    private SezonRepository sezonRepository;
    @ResponseBody
    @RequestMapping(value="/save")
    public ResponseEntity<SvopMessage> update(@RequestBody ArrayList<Sezon> sezons) {

        List<Sezon> sezons_saved=sezonRepository.saveAll(sezons);
        //System.out.println(sezons_saved);
        return new ResponseEntity<>(new Success("Success"), HttpStatus.OK);
    }
}
