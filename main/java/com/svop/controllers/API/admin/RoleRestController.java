package com.svop.controllers.API.admin;

import com.svop.View.Auth.PermissionsView;
import com.svop.View.Auth.RolePermissionsView;
import com.svop.View.Auth.RoleView;
import com.svop.View.Auth.UserRoleView;
import com.svop.exeptions.response.SvopMessage;
import com.svop.message.Success;
import com.svop.service.secutity.RoleServiceImpl;
import com.svop.tables.Users.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value="/svop/api/roles",headers = {"Content-type=application/json"})
public class RoleRestController {
    @Autowired private RoleRepository roleRepository;
    @Autowired private RolePermissionRepository rolePermissionRepository;
    @Autowired private RoleServiceImpl roleServiceImpl;

    @ResponseBody
    @RequestMapping(value="/getRole")
    public ResponseEntity<String> getRole(@RequestBody Integer id) {
       Optional<Role> role=roleRepository.findById(id);

        if (role.isPresent())
        {
            return new ResponseEntity<>(role.get().getName(),HttpStatus.OK) ;
        }
        else{
            return new ResponseEntity<>("",HttpStatus.OK) ;
        }

    }
    @ResponseBody
    @RequestMapping(value="/getPermissionsByRole")
    public ResponseEntity<List<PermissionsView>>  getPerrmissiond(@RequestBody Integer id) {

    return new ResponseEntity<>(roleServiceImpl.getPermissionsByRole(id), HttpStatus.OK);
    }
    @ResponseBody
    @RequestMapping(value="/save")
    public ResponseEntity<SvopMessage> save(@RequestBody RolePermissionsView rolePermissionsView) {
        roleServiceImpl.savePermissionsForRole(rolePermissionsView);
        return new ResponseEntity<>(new Success(),HttpStatus.OK);
    }

    //-----------------------Роли
    @ResponseBody
    @RequestMapping(value="/getAllRolesByUser")
    public ResponseEntity<List<RoleView>> getAllRolesByUser(@RequestBody Integer id) {
    return new ResponseEntity<>(roleServiceImpl.getAllRoleByUser(id),HttpStatus.OK);

    }

    @ResponseBody
    @RequestMapping(value="/saveUserRoles")
    public ResponseEntity<SvopMessage> saveUserRoles(@RequestBody UserRoleView rolePermissionsView) {
        roleServiceImpl.saveRolesForUser(rolePermissionsView);
        return new ResponseEntity<>(new Success(),HttpStatus.OK);
    }
}
