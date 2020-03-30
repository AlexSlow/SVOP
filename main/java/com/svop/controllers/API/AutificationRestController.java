package com.svop.controllers.API;

import com.svop.View.Auth.UserView;
import com.svop.service.secutity.SecurityService;
import com.svop.service.secutity.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value="/svop/public/api/auth")
public class AutificationRestController {
private @Autowired
SecurityService securityService;
    @ResponseBody
    @RequestMapping(value="/")
    public ResponseEntity<String> auth(@RequestBody UserView user) {

        if ((user.getUsername()!=null)&&(user.getPassword()!=null)) {
            securityService.autoLogin(user.getUsername(), user.getPassword());
        }
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}
