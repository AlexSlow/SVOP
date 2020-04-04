package com.svop.controllers.sezon;

import com.itextpdf.text.DocumentException;
import com.svop.other.HeadProcessing.Head_parser;
import com.svop.other.HeadProcessing.PageFormatter;
import com.svop.service.SeazonSchedule.SeazonScheduleService;
import com.svop.service.documentOutputPdf.SeazonOutputPdfService;
import com.svop.service.secutity.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Controller
public class SeasonScheduleHttpController {
    @Autowired
    private UserService userService;
    @Autowired private SeazonScheduleService seazonScheduleService;
    @Autowired private SeazonOutputPdfService seazonOutputPdfService;
    @RequestMapping(value="/svop/SeasonSchedule")

    public String open( Model model,@PageableDefault(sort = {"id"},direction = Sort.Direction.DESC) Pageable page) {
        Head_parser head_parser=new Head_parser();

        PageFormatter pageFormatter=new PageFormatter();
        head_parser.setModel(userService,model);
        model.addAttribute("sezonSchedules",seazonScheduleService.getSeazonScheduleViews(pageFormatter,page));
        pageFormatter.fillModel(model,page.getPageNumber());
        return "/html/SeasonSchedule/SeasonScheduleFormation.html";
    }
    @RequestMapping(value="/svop/SeasonSchedule/forming")
    public String forming( @RequestParam (name="formation",required = false) String forming_bt,
                           @RequestParam (name="pdf",required = false) String pdf_bt,
                           Model model) {

        //Сформировать расписание
        if (forming_bt!=null)
        {
            seazonScheduleService.forming();
        }
        if (pdf_bt!=null)
        {
            return "redirect:/svop/SeasonSchedule/pdf";
        }
        return "redirect:/svop/SeasonSchedule";
    }
@RequestMapping(value = "/svop/SeasonSchedule/pdf")
public ResponseEntity<InputStreamResource> get() throws IOException, DocumentException {

    ByteArrayInputStream bis = seazonOutputPdfService.generate(seazonScheduleService.getSeazonScheduleViews());

    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Disposition", "inline; filename=customers.pdf");
    return ResponseEntity
            .ok()
            .headers(headers)
            .contentType(MediaType.APPLICATION_PDF)
            .body(new InputStreamResource(bis));
}
    @RequestMapping(value="/svop/SeasonSchedule/tablo")
    public String tablo( Model model,@PageableDefault(sort = {"id"},direction = Sort.Direction.DESC) Pageable page) {

        PageFormatter pageFormatter=new PageFormatter();
        model.addAttribute("sezonSchedules",seazonScheduleService.getSeazonScheduleViews(pageFormatter,page));
        return "/html/SeasonSchedule/tablo.html";
    }

}
