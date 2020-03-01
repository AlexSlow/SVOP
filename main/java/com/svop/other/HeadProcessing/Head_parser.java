package com.svop.other.HeadProcessing;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

public class Head_parser {

    public static void setModel(Model model)
    {
        Localformatter localformatter=new Localformatter();
       //Тут должен быть запрос из БД
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("username",auth.getName());
        model.addAttribute("language",localformatter.getLocallabel());
    }

}
