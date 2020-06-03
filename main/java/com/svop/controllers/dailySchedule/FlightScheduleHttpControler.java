package com.svop.controllers.dailySchedule;

import com.svop.exeptions.httpResponse.DeleteFromDBExeption;
import com.svop.other.HeadProcessing.Head_parser;
import com.svop.other.HeadProcessing.PageFormatter;
import com.svop.service.dailySchedule.FlightSheduleDaoService;
import com.svop.service.secutity.UserService;
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

import java.util.List;

@Controller
@RequestMapping(value = "/svop")
public class FlightScheduleHttpControler {
    @Autowired private UserService userService;
    @Autowired private FlightSheduleDaoService flightSheduleDaoService;
    @RequestMapping(value = "FlightShedule")
    public String open(Model model, @PageableDefault(sort = {"day"}, direction = Sort.Direction.DESC) Pageable page) {
        Head_parser head_parser = new Head_parser();

        PageFormatter pageFormatter = new PageFormatter();
        head_parser.setModel(userService, model);
        model.addAttribute("flightSchedules", flightSheduleDaoService.getPage(pageFormatter,page));
        pageFormatter.fillModel(model, page.getPageNumber());
        return "/html/DailySchedule/FlightShedule.html";
    }

}
