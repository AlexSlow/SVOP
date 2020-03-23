package com.svop.controllers.HendBookControllers;

import com.svop.other.HeadProcessing.Head_parser;
import com.svop.service.handbooks.ReysyService;
import com.svop.service.secutity.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ReysyHttpController {
    @Autowired
    UserService userService;
    @Autowired
    ReysyService reysyService;

    @RequestMapping(value="svop/reysy")
    public String open( Model model) {
        Head_parser head_parser=new Head_parser();
        head_parser.setModel(userService,model);
        model.addAttribute("reysy",reysyService.get());
        return "/html/hendbooks/reysy.html";
    }
    @RequestMapping(value="svop/reysy/delete")
    public String delete(@RequestParam(name="ch[]",required = false) List<Integer> id_list) {
        /*
        if (id_list!=null)
            nomerReysService.delete(id_list);
        */
        return "redirect:/svop/reysy";
    }
}
