package com.svop.controllers.admin;

import com.svop.exeptions.httpResponse.DeleteFromDBExeption;
import com.svop.other.HeadProcessing.Head_parser;
import com.svop.other.HeadProcessing.PageFormatter;
import com.svop.service.secutity.UserService;
import com.svop.tables.Users.RoleRepository;
import com.svop.tables.Users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping(value="/svop/admin")
public class UserHttpController {

    @Autowired
    private UserService userService;
    @RequestMapping(value="users")
    public String open(Model model,@PageableDefault(sort = {"id"},direction = Sort.Direction.DESC) Pageable page) {
        Head_parser head_parser=new Head_parser();
        head_parser.setModel(userService,model);
        //  System.out.println(roleRepository.findAll());
        Page<User> users= userService.findAll(page);
        PageFormatter pageFormatter=new PageFormatter();
        pageFormatter.setSize(users.getTotalPages());
        pageFormatter.fillModel(model,page.getPageNumber());
        model.addAttribute("users",users);
        return "/html/admin/users.html";
    }
    @RequestMapping(value="users/delete")
    public String delete(@RequestParam(name="ch[]",required = false) List<Integer> id_list, RedirectAttributes redirectAttributes) {
        if (id_list!=null)
            try {
                userService.deleteByIdIn(id_list);
            }catch (DataIntegrityViolationException ex)
            {
                new DeleteFromDBExeption(redirectAttributes,ex.getLocalizedMessage());
            }
        return "redirect:/svop/admin/users";
    }
}
