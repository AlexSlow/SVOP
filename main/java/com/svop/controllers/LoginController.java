package com.svop.controllers;

import com.svop.service.secutity.SecurityService;
import com.svop.service.secutity.UserService;
import com.svop.tables.Users.User;
import com.svop.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;



    @RequestMapping(value="/svop/registration",method = RequestMethod.GET)
    public String registration( Model model) {

        model.addAttribute("user",new User());
        return "/html/registration.html";
    }

    @RequestMapping(value="/svop/registration",method = RequestMethod.POST)
    public String registration(@ModelAttribute("User") User user, BindingResult bindingResult, Model model) {
        /*
    }
    public String registration( @RequestParam(name = "username") String  username,
                                @RequestParam(name = "password") String password,
                                @RequestParam(name = "confirmPassword") String confirm_password,Model model) {
*/
        userValidator.validate(user,bindingResult);
        if (bindingResult.hasErrors())
        {
            for (Object object : bindingResult.getAllErrors()) {
                if(object instanceof FieldError) {
                    FieldError fieldError = (FieldError) object;
                    model.addAttribute(fieldError.getCode(),true);
                }

            }

            return "/html/registration.html";
        }else
        {
            userService.save(user);
            securityService.autoLogin(user.getUsername(),user.getPassword());
            return "redirect:/svop/";
        }

    }


    @RequestMapping(value="/svop/login")
    public String login(@RequestParam(name = "username",required = false) String username,
                        @RequestParam(name = "password",required = false) String password, Model model) {
        //System.out.println("user= "+username);
        if ((username!=null)&&(password!=null)) {
            securityService.autoLogin(username, password);
            return "redirect:/svop/";
        }else{
            return "/html/login.html";
        }

    }



}
