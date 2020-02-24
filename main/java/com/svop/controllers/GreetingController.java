package com.svop.controllers;

import com.svop.HeadProcessing.Head_parser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {

    @RequestMapping("/svop/greeting")
    public String setLocale(@RequestParam(name="flags",required = false)String flags,
                           @RequestParam(name="page",required = false)String page, Model model)
 {
       // System.out.println(page+" "+flags);
        return "hello";
 }

    @RequestMapping("/svop/")
    public String index(Model model)
    {
        Head_parser.setModel(model);

        return "content/html/start_page";
    }
}
