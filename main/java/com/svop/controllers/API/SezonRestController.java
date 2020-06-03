package com.svop.controllers.API;

import com.svop.exeptions.SvopDataBaseExeption;
import com.svop.exeptions.response.SvopMessage;
import com.svop.message.Success;
import com.svop.tables.Handbooks.Airporty;
import com.svop.tables.Handbooks.Sezon;
import com.svop.tables.Handbooks.SezonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value="/svop/api/sezons",headers = {"Content-type=application/json"})
public class SezonRestController {
    @Autowired
    private SezonRepository sezonRepository;
    @ResponseBody
    @RequestMapping(value="/")
    public ResponseEntity<SvopMessage> update(@RequestBody ArrayList<Sezon> sezons) {

        List<Sezon> sezons_saved=sezonRepository.saveAll(sezons);
        return new ResponseEntity<>(new Success("Success"), HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping(value="/get")
    public ResponseEntity<List<Sezon>> get(@RequestBody Map<String,Integer> page) {
        if (page==null){
            return new ResponseEntity(sezonRepository.findAll(),HttpStatus.OK);
        }

        try {
            Pageable pageable = PageRequest.of(page.get("page"),page.get("size"), Sort.by("id").descending());
            return new ResponseEntity(sezonRepository.findAll(pageable),HttpStatus.OK);
        }catch (DataIntegrityViolationException ex)
        {
            throw new SvopDataBaseExeption(ex.getLocalizedMessage());
        }
    }

    @ResponseBody
    @DeleteMapping(value="/")
    public ResponseEntity delete(@RequestBody List<Integer> id) {
        try {
            sezonRepository.deleteByIdIn(id);
            return new ResponseEntity("Success",HttpStatus.OK);
        }catch (Exception ex)
        {
            throw new SvopDataBaseExeption(ex.getLocalizedMessage());
        }
    }
}
