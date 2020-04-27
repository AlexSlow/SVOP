package com.svop.controllers.API.admin;

import com.svop.View.Auth.PermissionsView;
import com.svop.View.Auth.RolePermissionsView;
import com.svop.View.Auth.RoleView;
import com.svop.exeptions.SvopDataBaseExeption;
import com.svop.exeptions.response.SvopMessage;
import com.svop.message.Success;
import com.svop.service.secutity.RoleService;
import com.svop.tables.Users.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value="/svop/api/roles",headers = {"Content-type=application/json"})
public class RoleRestController {
    @Autowired private RoleRepository roleRepository;
    @Autowired private RolePermissionRepository rolePermissionRepository;
    @Autowired private RoleService roleService;
    /*
    @ResponseBody
    @RequestMapping(value="/save")
    public ResponseEntity<SvopMessage> update(@RequestBody List<RoleView> roleViews) {
        try {
            List<Role> permissions=new ArrayList<>(roleViews.size());
          for (RoleView roleView:roleViews)
          {
              if (roleView.getId()==null) {
                 permissions.add(new Role(roleView.getName()));
              }else
              {
                  permissions.add(new Role(roleView.getId(), roleView.getName()));
              }
          }
          roleRepository.saveAll(permissions);
        }catch (DataIntegrityViolationException ex)
        {
            throw new SvopDataBaseExeption(ex.getLocalizedMessage());
        }
        return new ResponseEntity<SvopMessage>(new Success("Success"), HttpStatus.OK);

    }

     */
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
    public List<PermissionsView> getPerrmissiond(@RequestBody Integer id) {

    return roleService.getPermissionsByRole(id);
    }
    @ResponseBody
    @RequestMapping(value="/save")
    public ResponseEntity<SvopMessage> save(@RequestBody RolePermissionsView rolePermissionsView) {
        roleService.savePermissionsForRole(rolePermissionsView);
        return new ResponseEntity<>(new Success(),HttpStatus.OK);
    }
}
