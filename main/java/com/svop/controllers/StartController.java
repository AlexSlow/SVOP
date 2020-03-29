package com.svop.controllers;

import com.svop.other.HeadProcessing.Head_parser;
import com.svop.service.secutity.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;
import java.util.stream.Collectors;


@Controller
public class StartController {

    @Autowired
    UserService userService;
    @RequestMapping("/svop")
    public String index(Model model)
    {
        Head_parser head_parser=new Head_parser();
        head_parser.setModel(userService,model);
        model.addAttribute("usersAccounts",userService.getUserAccountsInfo());
        return "/html/start_page";
    }
}
