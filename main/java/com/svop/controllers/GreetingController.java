package com.svop.controllers;

import com.svop.Localformatter;
import com.svop.tables.handbooks.Repository.Airporty_repository;
import com.svop.tables.handbooks.Airporty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class GreetingController {
    @Autowired
    private Airporty_repository Airports_repo;
    @GetMapping("/svop/greeting")
    public String greeting(Model model) {
        Iterable<Airporty> Airports=Airports_repo.findAll();
        model.addAttribute("airports",Airports);
        return "hello";
    }

    @RequestMapping("/svop/")
    public String index(@RequestParam(name="leng_change",required = false)String language_bt,
                        @RequestParam(name="flags",required = false)String flags,
                        @RequestParam(name="exit",required = false)Boolean isExit, Model model)
    {
        if (language_bt!=null)
        {

        }
        Localformatter localformatter=new Localformatter();
        model.addAttribute("language",localformatter.getLocallabel());

        return "content/html/start_page";
    }
}
