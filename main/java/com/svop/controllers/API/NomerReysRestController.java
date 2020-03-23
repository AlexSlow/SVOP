package com.svop.controllers.API;

import com.svop.View.NomerReysView;
import com.svop.View.NomerReysViewRequest;
import com.svop.service.handbooks.NomerReysService;
import com.svop.tables.Handbooks.NomerReys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value="svop/api/nomer_reys",headers = {"Content-type=application/json"})
public class NomerReysRestController {
    @Autowired
    NomerReysService nomerReysService;

    @ResponseBody
    @RequestMapping(value="/get_from_aircompanies")
    public List<NomerReysView> get(@RequestBody Integer aircompany_id) {
        // System.out.println(aircompany_id);
        return nomerReysService.getNomerReysFromAircompany(aircompany_id);
    }
    @ResponseBody
    @RequestMapping(value="/save")
    public ResponseEntity<String> save(@RequestBody NomerReysViewRequest nomerReysView) {
        return nomerReysService.save(nomerReysView) ;
    }

}
