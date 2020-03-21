package com.svop.controllers.API;

import com.svop.View.RoutesView;
import com.svop.service.handbooks.RoutesService;
import com.svop.tables.Handbooks.Airporty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value="svop/api/routes",headers = {"Content-type=application/json"})
public class RoutesRestController {
    @Autowired
    RoutesService routesService;

    @ResponseBody
    @RequestMapping(value="/save")
    public ResponseEntity<String> update(@RequestBody ArrayList<RoutesView> routes) {
        return routesService.save(routes);
    }
    /*
    @ResponseBody
    @RequestMapping(value="/save")
    public  String delete(@RequestBody List<RoutesView> routes) {
        routesService.deleteRoutesList(routes);
        return "Success";
    }
    */

}
