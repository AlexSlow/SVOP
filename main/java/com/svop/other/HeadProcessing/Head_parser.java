package com.svop.other.HeadProcessing;

import com.svop.service.secutity.UserService;
import com.svop.service.secutity.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.Locale;
public class Head_parser {
    public  void setModel(UserService userService,Model model)
    {
        Localformatter localformatter=new Localformatter();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String locale=userService.getLocale(auth.getName());
       //Тут должен быть запрос из БД

        model.addAttribute("username",auth.getName());
        model.addAttribute("language",localformatter.getLocallabel(locale));
        model.addAttribute("current_language",locale);
       // Locale.setDefault(new Locale(locale));
    }

}
