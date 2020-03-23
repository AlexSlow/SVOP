package com.svop.controllers.HendBookControllers;
import com.svop.other.HeadProcessing.Head_parser;
import com.svop.service.handbooks.RoutesService;
import com.svop.service.secutity.UserService;
import com.svop.tables.Handbooks.AirportyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RouteHttpController {
    @Autowired
    private RoutesService routesService;
    @Autowired
    private AirportyRepo airportyRepo;
    @Autowired
    UserService userService;
    @RequestMapping(value="svop/routs")
    public String open( Model model) {
        Head_parser head_parser=new Head_parser();
        head_parser.setModel(userService,model);
        model.addAttribute("routs",routesService.getRouts());
        model.addAttribute("airports",airportyRepo.findAll());
        return "/html/hendbooks/routs.html";
    }


    @RequestMapping(value="svop/routs/delete")
    public String delete(@RequestParam(name="ch[]",required = false) List<Integer> id_list, Model model) {
        if (id_list!=null)
        routesService.delete(id_list);
        return "redirect:/svop/routs";
    }
}
