package com.svop.controllers.http.dailySchedule;

import com.itextpdf.text.DocumentException;
import com.svop.View.DailyScheduleViews.FlightScheduleView;
import com.svop.message.Period;
import com.svop.other.HeadProcessing.Head_parser;
import com.svop.other.HeadProcessing.PageFormatter;
import com.svop.service.dailySchedule.FlightSheduleDaoService;
import com.svop.service.documentOutputPdf.FlightSheduleOutputPdf;
import com.svop.service.secutity.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/svop")
public class FlightScheduleHttpControler {
    @Autowired private UserService userService;
    @Autowired private FlightSheduleDaoService flightSheduleDaoService;
    @Autowired private FlightSheduleOutputPdf flightSheduleOutputPdf;
    @RequestMapping(value = "FlightShedule")
    public String open(Model model, @PageableDefault(sort = {"day"}, direction = Sort.Direction.DESC) Pageable page) {
        Head_parser head_parser = new Head_parser();
        PageFormatter pageFormatter = new PageFormatter();
        head_parser.setModel(userService, model);
        model.addAttribute("flightSchedules", flightSheduleDaoService.getPage(pageFormatter,page));
        pageFormatter.fillModel(model, page.getPageNumber());
        return "/html/DailySchedule/FlightShedule.html";
    }

    @RequestMapping(value = "FlightSchedule/view")
    public String open_document_forming_page(Model model) {
        Head_parser head_parser = new Head_parser();
        head_parser.setModel(userService, model);
        return "/html/DailySchedule/FlightScheduleDocumentForming.html";
    }

    @RequestMapping(value = "FlightSchedule/pdf")
    public ResponseEntity<InputStreamResource> getPdf(@ModelAttribute Period period) throws IOException, DocumentException {
        //Сформировать расписание
        if (period.getStart()==null)period.setStart(new Date(0));
        if (period.getEnd()==null)period.setEnd(new Date(System.currentTimeMillis()));
        List<FlightScheduleView> list= flightSheduleDaoService.findBetweenPeriod(period.getStart(),period.getEnd());
        ByteArrayInputStream bis= flightSheduleOutputPdf.generate(period,list, SecurityContextHolder.getContext().getAuthentication().getName());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=FlightShedule.pdf");
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    @RequestMapping(value="FlightSchedule/manager")
    public String getTabloInfomanagerPage( Model model) {
        Head_parser head_parser=new Head_parser();
        head_parser.setModel(userService,model);
        return "/html/control/infoTabloManager.html";
    }

    @RequestMapping(value="FlightSchedule/tablo")
    public String getTabloInfo( Model model) {
        Head_parser head_parser=new Head_parser();
        head_parser.setModel(userService,model);
        return "/html/DailySchedule/TabloInfo.html";
    }


}
