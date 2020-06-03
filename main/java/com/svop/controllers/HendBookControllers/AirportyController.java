package com.svop.controllers.HendBookControllers;

import com.svop.exeptions.SvopDataBaseExeption;
import com.svop.exeptions.httpResponse.DeleteFromDBExeption;
import com.svop.other.HeadProcessing.Head_parser;
import com.svop.other.HeadProcessing.PageFormatter;
import com.svop.service.handbooks.AirportsService;
import com.svop.service.secutity.UserService;
import com.svop.tables.Handbooks.Airporty;
import com.svop.tables.Handbooks.AirportyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QSort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
public class AirportyController {
    @Autowired
   // private AirportyRepo AirportsRepositiry;
    private AirportsService airportsService;
    @Autowired
    UserService userService;
    @RequestMapping(value="/svop/airports")
    public String open(Model model,@PageableDefault(sort = {"nameRu"},direction = Sort.Direction.ASC) Pageable page) {
        Head_parser head_parser=new Head_parser();
        head_parser.setModel(userService,model);
        Page<Airporty> Airports=airportsService.getPage(page);
        PageFormatter pageFormatter=new PageFormatter();
        pageFormatter.setSize(Airports.getTotalPages());
        pageFormatter.fillModel(model,page.getPageNumber());
        model.addAttribute("airports",Airports);
        return "/html/hendbooks/airports.html";
    }


    @RequestMapping(value="/svop/airports/delete")
    public String delete(@RequestParam(name="ch[]",required = false) List<Integer> id_list, Model model, RedirectAttributes redirectAttributes) {
        if (id_list!=null)
            try {
                airportsService.deleteById(id_list);
            }catch (DataIntegrityViolationException ex)
            {
                new DeleteFromDBExeption(redirectAttributes,ex.getLocalizedMessage());
            }
        return "redirect:/svop/airports";
    }

}