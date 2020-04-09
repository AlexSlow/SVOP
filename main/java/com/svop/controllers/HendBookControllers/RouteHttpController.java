package com.svop.controllers.HendBookControllers;
import com.svop.exeptions.httpResponse.DeleteFromDBExeption;
import com.svop.other.HeadProcessing.Head_parser;
import com.svop.other.HeadProcessing.PageFormatter;
import com.svop.service.handbooks.RoutesService;
import com.svop.service.secutity.UserService;
import com.svop.tables.Handbooks.AirportyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RouteHttpController {
    @Autowired
    private RoutesService routesService;
    @Autowired
    private AirportyRepo airportyRepo;
    @Autowired
    UserService userService;
    @RequestMapping(value="/svop/routs")
    public String open( Model model,@PageableDefault(sort = {"id"},direction = Sort.Direction.DESC) Pageable page) {
        Head_parser head_parser=new Head_parser();
        head_parser.setModel(userService,model);
        PageFormatter pageFormatter=new PageFormatter();
        model.addAttribute("routs",routesService.getPageRouts(pageFormatter,page));
        pageFormatter.fillModel(model,page.getPageNumber());
        model.addAttribute("airports",airportyRepo.findAll());
        return "/html/hendbooks/routs.html";
    }


    @RequestMapping(value="/svop/routs/delete")
    public String delete(@RequestParam(name="ch[]",required = false) List<Integer> id_list, Model model, RedirectAttributes redirectAttributes) {
        if (id_list!=null)
            try {
                routesService.delete(id_list);
            }
             catch (DataIntegrityViolationException ex)
        {
            new DeleteFromDBExeption(redirectAttributes,ex.getLocalizedMessage());
        }

        return "redirect:/svop/routs";
    }
}
