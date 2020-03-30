package com.svop.controllers.HendBookControllers;

import com.svop.other.HeadProcessing.Head_parser;
import com.svop.service.handbooks.AircompaniesService;
import com.svop.service.handbooks.NomerReysService;
import com.svop.service.secutity.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class NomerReysHttpController {
    @Autowired
    UserService userService;
    @Autowired
    AircompaniesService aircompaniesService;
    @Autowired
    NomerReysService nomerReysService;
    //Тут передача выбранного селекта.Модель через ридерект не передается
    //Возможно его видят все пользователи
    //private Integer aircompany_selected;
    @RequestMapping(value="/svop/nomer_reys")
    public String open(@RequestParam(name="aircompany_select",required = false) Integer aircompany,Model model) {
        Head_parser head_parser=new Head_parser();
        head_parser.setModel(userService,model);
        if (aircompany!=null)
        {
        model.addAttribute("aircompanies_selected",aircompany);
        }
        model.addAttribute("aircompanies",aircompaniesService.getAircompanies());
        return "/html/hendbooks/nomer_reys.html";
    }
    @RequestMapping(value="/svop/nomer_reys/delete")
    public String delete(@RequestParam(name="aircompany_select",required = false) Integer aircompany,
                         @RequestParam(name="ch[]",required = false) List<Integer> id_list,
                         RedirectAttributes redirectAttributes) {
        //Для передачи параметров между редиректами
        redirectAttributes.addFlashAttribute("aircompanies_selected",aircompany);
        if (id_list!=null)
        nomerReysService.delete(id_list);
        return "redirect:/svop/nomer_reys";
    }
}
