package com.svop.controllers.http.admin;

import com.svop.exeptions.httpResponse.DeleteFromDBExeption;
import com.svop.other.HeadProcessing.Head_parser;
import com.svop.service.secutity.UserService;
import com.svop.tables.Users.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping(value="/svop/admin")
public class RoleHttpController {

    @Autowired
   private RoleRepository roleRepository;
    @Autowired
   private UserService userService;
    @RequestMapping(value="roles")
    public String open(Model model) {
        Head_parser head_parser=new Head_parser();
        head_parser.setModel(userService,model);
      //  System.out.println(roleRepository.findAll());
        model.addAttribute("roles", roleRepository.findAll());
        return "/html/admin/roles.html";
    }
    @RequestMapping(value="roles/delete")
    public String delete(@RequestParam(name="ch[]",required = false) List<Integer> id_list, RedirectAttributes redirectAttributes) {
        if (id_list!=null)
            try {
                roleRepository.deleteByIdIn(id_list);
            }catch (DataIntegrityViolationException ex)
            {
                new DeleteFromDBExeption(redirectAttributes,ex.getLocalizedMessage());
            }
        return "redirect:/svop/admin/roles";
    }
}
