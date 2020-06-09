package com.svop.controllers.http.HendBookControllers;

import com.svop.other.HeadProcessing.Head_parser;
import com.svop.other.HeadProcessing.PageFormatter;
import com.svop.service.secutity.UserService;
import com.svop.tables.Handbooks.Sezon;
import com.svop.tables.Handbooks.SezonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SezonHttpController {
    @Autowired
    private UserService  userService;
    @Autowired
    private SezonRepository sezonRepository;
    @RequestMapping(value="/svop/sezons")
    public String open( Model model,@PageableDefault(sort = {"id"},direction = Sort.Direction.DESC) Pageable page) {
        Head_parser head_parser=new Head_parser();
        head_parser.setModel(userService,model);
        Page<Sezon> sezons=sezonRepository.findAll(page);
        PageFormatter pageFormatter=new PageFormatter(sezons.getTotalPages());
        pageFormatter.fillModel(model,page.getPageNumber());
        model.addAttribute("sezons",sezons);

        return "/html/hendbooks/sezons.html";
    }


    @RequestMapping(value="/svop/sezons/delete")
    public String delete(@RequestParam(name="ch[]",required = false) List<Integer> id_list, Model model) {
        if (id_list!=null)
            sezonRepository.deleteByIdIn(id_list);
        return "redirect:/svop/sezons";
    }
}
