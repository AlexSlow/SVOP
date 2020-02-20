package com.svop.controllers;

import com.svop.tables.handbooks.Airporty;
import com.svop.tables.handbooks.Repository.Airporty_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HendBookController {
    @Autowired
    private Airporty_repository Airports_repo;
    @RequestMapping(value="svop/handbooks/airports_opening")
    public String airports_open(@RequestParam(name="ch[]",required = false)List id_set, Model model) {

        Iterable<Airporty> Airports=Airports_repo.findAll();
        model.addAttribute("airports",Airports);
        return "content/html/hendbooks/airports_opening.html";
    }

}