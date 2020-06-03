package com.svop.controllers.API;

import com.svop.View.Auth.UserView;
import com.svop.service.secutity.SecurityService;
import com.svop.service.secutity.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value="/svop/public/api/auth")
public class AutificationRestController {
private @Autowired
SecurityService securityService;
    @ResponseBody
    @PostMapping(value="/")
    public ResponseEntity<String> auth(@RequestBody UserView user) {

        System.out.println("Получить токен");
        if ((user.getUsername()!=null)&&(user.getPassword()!=null)) {
            securityService.autoLogin(user.getUsername(), user.getPassword());
        }
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
    @ResponseBody
    @GetMapping(value="/test")
    public ResponseEntity<String> test() {

        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}
