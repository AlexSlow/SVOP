package com.svop.controllers.dailySchedule;
import com.itextpdf.text.DocumentException;
import com.svop.View.DailyScheduleViews.DailyScheduleView;
import com.svop.message.Period;
import com.svop.other.HeadProcessing.Head_parser;
import com.svop.other.HeadProcessing.PageFormatter;
import com.svop.service.dailySchedule.DailyDaoService;
import com.svop.service.dailySchedule.DailySheduleService;
import com.svop.service.dailySchedule.StoicDaoService;
import com.svop.service.documentOutputPdf.DailyPlaneOutputPdfService;
import com.svop.service.secutity.UserService;
import com.svop.tables.daily_schedule.Stoic;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.List;


@Controller
@RequestMapping(value = "/svop")
public class DailyScheduleHttpController {
    @Autowired
    private UserService userService;
    @Autowired
    private DailySheduleService dailySheduleService;
    @Autowired private DailyDaoService dailyDaoService;
    @Autowired
    private  DailyPlaneOutputPdfService dailyPlaneOutputPdfService;
    @Autowired
    private StoicDaoService stoicDaoService;

    @RequestMapping(value = "DailySchedule")
    public String open(Model model, @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable page) {
        Head_parser head_parser = new Head_parser();

        PageFormatter pageFormatter = new PageFormatter();
        head_parser.setModel(userService, model);
        model.addAttribute("dailySchedules", dailyDaoService.getPageList(pageFormatter, page));
        pageFormatter.fillModel(model, page.getPageNumber());
        return "/html/DailySchedule/DailyScheduleFormation.html";
    }

    @RequestMapping(value = "DailySchedule/view")
    public String open_document_forming_page(Model model) {
        Head_parser head_parser = new Head_parser();
        head_parser.setModel(userService, model);
        return "/html/DailySchedule/DailyDocumentForming.html";
    }

    /**
     * Прием запроса на формирование сезонного расписания
     *
     * @param forming_bt
     * @param model
     * @return
     */
    @RequestMapping(value = "DailySchedule/forming")
    public String forming(@RequestParam(name = "formation") String forming_bt,
                          Model model, RedirectAttributes redirectAttributes) {

        //Сформировать расписание
            try {
                dailySheduleService.forming();
            }
             catch (Exception ex)
            {
                ex.printStackTrace();
              redirectAttributes.addFlashAttribute("errorMenu",ex.getLocalizedMessage());
            }
        return "redirect:/svop/DailySchedule";
    }
    @RequestMapping(value = "DailySchedule/pdf")
    public ResponseEntity<InputStreamResource> getPdf(@ModelAttribute Period period) throws IOException, DocumentException {
        //Сформировать расписание
        if (period.getStart()==null)period.setStart(new Date(0));
        if (period.getEnd()==null)period.setEnd(new Date(System.currentTimeMillis()));
              List<DailyScheduleView> list= dailyDaoService.getDailyViewBetwinPeriod(period.getStart(),period.getEnd());
              ByteArrayInputStream bis= dailyPlaneOutputPdfService.generate(period,list);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=DailyByPeriod.pdf");
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    /**
     * Страница табло
     * @param model
     * @return
     */
    @RequestMapping(value="DailySchedule/tablo")
    public String tablo( Model model) {
        return "/html/DailySchedule/tablo.html";
    }

    /**
     * Страница управления таблом
     * @param model
     * @return
     */
    @RequestMapping(value="DailySchedule/control")
    public String getControlWindow( Model model) {
        Head_parser head_parser=new Head_parser();
        head_parser.setModel(userService,model);
        return "/html/control/daily_tablo_managment.html";
    }
    /**
     * Открыть окно управления
     *
     */
    @RequestMapping(value="DailySchedule/stoicsManager")
    public String openStoicPage( Model model) {
        Head_parser head_parser=new Head_parser();
        head_parser.setModel(userService,model);
        model.addAttribute("stoics",stoicDaoService.findDtoAll());
        return "/html/DailySchedule/stoicManager.html ";
    }

    @RequestMapping(value="stoic/{stoic}")
    public String openStoicPage(@PathVariable("stoic") Stoic stoic, Model model) {
        model.addAttribute("stoic",stoicDaoService.getStoic(stoic.getId()));
        return "/html/DailySchedule/stoicTablo.html";
    }




}
