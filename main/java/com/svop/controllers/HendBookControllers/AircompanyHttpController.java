package com.svop.controllers.HendBookControllers;

import com.svop.View.AircompanyView;
import com.svop.exeptions.httpResponse.DeleteFromDBExeption;
import com.svop.other.HeadProcessing.Head_parser;
import com.svop.other.HeadProcessing.PageFormatter;
import com.svop.service.handbooks.AircompaniesService;
import com.svop.service.secutity.UserService;
import com.svop.tables.Handbooks.Aircompany;
import com.svop.tables.Handbooks.Airporty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AircompanyHttpController {
    @Autowired
    UserService userService;
    @Autowired
    AircompaniesService aircompaniesService;
    @RequestMapping(value="/svop/aircompanies")
    public String open(Model model,@PageableDefault(sort = {"id"},direction = Sort.Direction.DESC) Pageable page)
    {
        Head_parser head_parser=new Head_parser();
        head_parser.setModel(userService,model);
        PageFormatter pageFormatter=new PageFormatter();

        Page<Aircompany> aircompanies=aircompaniesService.getAircompanies(page);
        pageFormatter.setSize(aircompanies.getTotalPages());
        pageFormatter.fillModel(model,page.getPageNumber());
        model.addAttribute("aircompanies",aircompanies);
        return "/html/hendbooks/aircompanies.html";
    }
    @RequestMapping(value="/svop/aircompanies/delete")
    public String delete(@RequestParam(name="ch[]",required = false) List<Integer> id_list, Model model,RedirectAttributes redirectAttributes) {
        if (id_list!=null)
            try {
                aircompaniesService.delete(id_list);
            } catch (DataIntegrityViolationException ex)
            {
                new DeleteFromDBExeption(redirectAttributes,ex.getLocalizedMessage());
            }

        return "redirect:/svop/aircompanies";
    }
@RequestMapping(value="/svop/aircompanies/save")
public String update(@ModelAttribute AircompanyView aircompanyView, Model model, RedirectAttributes redirectAttributes) throws IOException {
        try{
            aircompaniesService.save(aircompanyView);
        }
    catch (DataIntegrityViolationException ex)
    {
        new DeleteFromDBExeption(redirectAttributes,ex.getLocalizedMessage());
    }
    return "redirect:/svop/aircompanies";
}
}
