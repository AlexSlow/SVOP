package com.svop.controllers.HendBookControllers;

import com.svop.other.HeadProcessing.Head_parser;
import com.svop.service.secutity.UserService;
import com.svop.tables.Handbooks.Airporty;
import com.svop.tables.Handbooks.AirportyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
public class AirportyController {
    @Autowired
    private AirportyRepo AirportsRepositiry;
    @Autowired
    UserService userService;
    @RequestMapping(value="svop/airports")
    public String open( Model model) {
        Head_parser head_parser=new Head_parser();
        head_parser.setModel(userService,model);
        Iterable<Airporty> Airports=AirportsRepositiry.findAll();
        model.addAttribute("airports",Airports);
        return "/html/hendbooks/airports.html";
    }


    @RequestMapping(value="svop/airports/delete")
    public String delete(@RequestParam(name="ch[]",required = false) List<Integer> id_list, Model model) {
        if (id_list!=null)
        AirportsRepositiry.deleteByIdIn(id_list);
        return "redirect:/svop/airports";
    }






}