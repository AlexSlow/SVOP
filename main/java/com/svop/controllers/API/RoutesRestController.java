package com.svop.controllers.API;

import com.svop.View.RoutesView;
import com.svop.exeptions.SvopDataBaseExeption;
import com.svop.service.handbooks.RoutesService;
import com.svop.tables.Handbooks.Airporty;
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
@RequestMapping(value="/svop/api/routes",headers = {"Content-type=application/json"})
public class RoutesRestController {
    @Autowired
    RoutesService routesService;

    @ResponseBody
    @RequestMapping(value="/")
    public ResponseEntity<String> update(@RequestBody ArrayList<RoutesView> routes) {
        try {
            return routesService.save(routes);
        }
        catch (DataIntegrityViolationException ex)
        {
            throw new SvopDataBaseExeption(ex.getLocalizedMessage());
        }

    }

    @ResponseBody
    @PostMapping(value="/get")
    public ResponseEntity<List<RoutesView>> get(@RequestBody Map<String,Integer> page) {
        if (page==null){
            return new ResponseEntity(routesService.getRouts(), HttpStatus.OK);
        }

        try {
            Pageable pageable = PageRequest.of(page.get("page"),page.get("size"), Sort.by("name").ascending());
            return new ResponseEntity(routesService.getRouts(pageable),HttpStatus.OK);
        }catch (DataIntegrityViolationException  ex)
        {
            throw new SvopDataBaseExeption(ex.getLocalizedMessage());
        }
    }

    @ResponseBody
    @DeleteMapping(value="/")
    public ResponseEntity delete(@RequestBody List<Integer> id) {
        try {
            routesService.delete(id);
            return new ResponseEntity("Success",HttpStatus.OK);
        }catch (Exception ex)
        {
            throw new SvopDataBaseExeption(ex.getLocalizedMessage());
        }
    }


}
