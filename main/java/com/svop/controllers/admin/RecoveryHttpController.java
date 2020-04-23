package com.svop.controllers.admin;

import com.svop.job.recovery.RecoveryJob;
import com.svop.other.HeadProcessing.Head_parser;
import com.svop.service.admin.Recovery;
import com.svop.service.secutity.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

@Controller
public class RecoveryHttpController {
    //Работа с properties
    @Autowired
    private Environment env;
    @Autowired
    private UserService userService;
    @Autowired
    private Recovery recovery;
    @Autowired
    private RecoveryJob recoveryJob;
    @RequestMapping(value="/svop/admin/recovery")
    public String open(Model model) {
        Head_parser head_parser=new Head_parser();
        head_parser.setModel(userService,model);
        model.addAttribute("period",env.getProperty("svop.backup_period"));
        try {
            model.addAttribute("dumps",recovery.getDumps());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "/html/admin/recovery.html";
    }

    @RequestMapping (value="/svop/admin/recovery/save")
    public String recover(Model model, @RequestParam(name = "save",required = false) String save_bt,
            @RequestParam(name = "period_create_savepoint_bt",required = false) String setPeriod_bt,
                          @RequestParam(name = "period_create_savepoint",required = false) Integer period,
                          RedirectAttributes redirectAttributes) throws IOException {
        if (save_bt!=null) {
            try {
                if (!recovery.export()) redirectAttributes.addFlashAttribute("message", "Ошибка дампа базы данных!");
                else redirectAttributes.addFlashAttribute("message", "Дамп базы данных прошел успешно");
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }else if(setPeriod_bt!=null){
            /*
        Properties properties=new Properties();
        properties.load(new FileReader("/application.properties"));
        properties.setProperty("svop.backup_period",period.toString());
        properties.store();
        */
        if (period==0)
        {
            recoveryJob.setStopFlag(false);
        }
        else
        {
            recoveryJob.setStopFlag(true);
        }
        }
        return "redirect:/svop/admin/recovery";
    }
}
