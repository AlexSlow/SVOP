package com.svop.controllers.admin;

import com.svop.other.HeadProcessing.Head_parser;
import com.svop.service.admin.Recovery;
import com.svop.service.secutity.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.sql.SQLException;

@Controller
public class RecoveryHttpController {
    @Autowired
    private UserService userService;
    @Autowired
    private Recovery recovery;
    @RequestMapping(value="/svop/admin/recovery")
    public String open(Model model) {
        Head_parser head_parser=new Head_parser();
        head_parser.setModel(userService,model);
        return "/html/admin/recovery.html";
    }

    @RequestMapping (value="/svop/admin/recovery/save")
    public String recover(Model model,@RequestParam(name = "save",required = false) String save_bt) {
        try {
            System.out.println("there");
            recovery.export();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "fovard:/svop/admin/recovery";
    }
}
