package com.svop.controllers.dailySchedule;

import com.svop.other.HeadProcessing.Head_parser;
import com.svop.other.HeadProcessing.PageFormatter;
import com.svop.service.dailySchedule.ShiftService;
import com.svop.service.secutity.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/svop")
public class ShiftHttpController {
    @Autowired private UserService userService;
    @Autowired  private ShiftService shiftService;
    @RequestMapping(value = "shifts")
    public String open(Model model, @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable page) {
        Head_parser head_parser = new Head_parser();

        PageFormatter pageFormatter = new PageFormatter();
        head_parser.setModel(userService, model);
        model.addAttribute("shifts", shiftService.findAll(pageFormatter,page));
        pageFormatter.fillModel(model, page.getPageNumber());
        return "/html/DailySchedule/Shifts.html";
    }
}
