package com.svop.controllers.API;

import com.svop.service.secutity.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/svop/api/language")
public class SelectLanguage {
    @Autowired
    private UserService userService;

    @RequestMapping(value="/select")
    public  void select( String locale) {
    userService.setLocale(SecurityContextHolder.getContext().getAuthentication().getName(),locale);
    }
}
