package com.svop.controllers.http.Journal;

import com.svop.tables.Handbooks.*;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.hibernate.envers.query.order.AuditOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HttpAuditController {
    private static final int limit=50;
    @Autowired
   private AuditReader auditReader;
    @GetMapping(value="/svop/journal/routs")
    public String routs(Model model) {
       // AuditQuery auditQuery=auditReader.createQuery().forRevisionsOfEntity(Routes.class,true,true).addOrder(AuditEntity.id().desc());
       //Выбрать по конкретному параметру   auditQuery.add()
       // List<Object> result=auditQuery.getResultList();
        model.addAttribute("body",getBody(Routes.class));
        return "/html/log.html";
    }

    @GetMapping(value="/svop/journal/airport")
    public String airport(Model model) {
        model.addAttribute("body", getBody(Airporty.class));
        return "/html/log.html";
    }

    @GetMapping(value="/svop/journal/nomer_reys")
    public String nomer_reys(Model model) {
        model.addAttribute("body",getBody(NomerReys.class));
        return "/html/log.html";
    }

    @GetMapping(value="/svop/journal/seazon")
    public String seazon(Model model) {
        model.addAttribute("body",getBody(Sezon.class));
        return "/html/log.html";
    }

    @GetMapping(value="/svop/journal/reysy")
    public String reysy(Model model) {
        model.addAttribute("body",getBody(Reysy.class));
        return "/html/log.html";
    }


    private List<Object> getBody(Class cls)
    {
        AuditQuery auditQuery=auditReader.createQuery().forRevisionsOfEntity(cls,true,true).addOrder(AuditEntity.id().desc());
        return auditQuery.getResultList();
    }

}
