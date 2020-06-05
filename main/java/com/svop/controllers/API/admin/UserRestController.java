package com.svop.controllers.API.admin;

import com.svop.exeptions.SvopDataBaseExeption;
import com.svop.service.secutity.UserDaoService;
import com.svop.service.secutity.UserService;
import com.svop.tables.Handbooks.Airporty;
import com.svop.tables.Users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/svop/api/admin")
public class UserRestController {
    @Autowired
    private UserService userService;
    @Autowired private UserDaoService userDaoService;
    @PostMapping("getUsers")
    @ResponseBody
    public ResponseEntity<List<com.svop.controllers.API.DTO.UserDto>> get(@RequestBody Map<String,Integer> page) {
        if (page==null){
            return new ResponseEntity(userService.findAll(), HttpStatus.OK);
        }
        try {
            Pageable pageable = PageRequest.of(page.get("page"),page.get("size"), Sort.by("username").ascending());
            return new ResponseEntity(userService.findAll(pageable),HttpStatus.OK);
        }catch (DataIntegrityViolationException ex)
        {
            throw new SvopDataBaseExeption(ex.getLocalizedMessage());
        }
    }
    @PostMapping("getUsersById")
    @ResponseBody
    public ResponseEntity<List<com.svop.controllers.API.DTO.UserDto>> getListById(@RequestBody List<Integer> list) {
        try {
            List<User> users=userService.find(list);

            return new ResponseEntity(userDaoService.usersDtoFactory(users),HttpStatus.OK);
        }catch (DataIntegrityViolationException ex)
        {
            throw new SvopDataBaseExeption(ex.getLocalizedMessage());
        }
    }

    @PostMapping("updateUsers")
    @ResponseBody
    public ResponseEntity update(@RequestBody List<com.svop.controllers.API.DTO.UserDto> users) {
       try {
           userDaoService.update(users);
          return new ResponseEntity<>("Успех",HttpStatus.OK);
       }catch (Exception e)
       {
           e.printStackTrace();
          throw  new SvopDataBaseExeption(e.getLocalizedMessage());
       }
    }

    @ResponseBody
    @PostMapping(value="/getPage")
    public ResponseEntity<List<com.svop.controllers.API.DTO.UserDto>> getPage(@RequestBody Map<String,Integer> page) {

        if (page==null){
           List<User> users=userService.findAll();
            return new ResponseEntity(userDaoService.usersDtoFactory(users),HttpStatus.OK);
        }
        try {
            Pageable pageable = PageRequest.of(page.get("page"),page.get("size"),Sort.by("username").ascending());
           Page<User> users=userService.findAll(pageable);
           System.out.println(users);
            return new ResponseEntity(userDaoService.usersDtoFactory(users),HttpStatus.OK);
        }catch (DataIntegrityViolationException  ex)
        {
            throw new SvopDataBaseExeption(ex.getLocalizedMessage());
        }
    }
}
