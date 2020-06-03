package com.svop.View.SeazonScheduleViews;

import com.svop.service.secutity.UserService;
import com.svop.tables.Handbooks.Airline;
import com.svop.tables.SeazonSchedule.SeazonSchedule;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.text.DateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class SeazonScheduleView {
    private Integer id;
    private String nomerPrilet;
    private String nomerVilet;
    private String routRu;
    private String routEn;
    private String routCh;
    private String periodStart;
    private String periodEnd;
    private String priletDays;
    private String prilet_time_otpravl;
    private String prilet_time_prib;
    private String viletDays;
    private String vilet_time_otpravl;
    private String vilet_time_prib;
    private String tip_vs;
    private Airline airline;
    private String aircompany;
    private String img;
    private String type;



    public SeazonScheduleView() {
    }

    public SeazonScheduleView(SeazonSchedule seazonSchedule,String locale)
    {
        if (seazonSchedule==null) return;
        if ((locale==null)||(locale.isEmpty()))
        {
            locale="ru";
        }
        this.id=seazonSchedule.getId();
        this.airline=seazonSchedule.getAirline();
        this.nomerPrilet=seazonSchedule.getNomerPrilet();
        this.nomerVilet=seazonSchedule.getNomerVilet();

        this.priletDays=seazonSchedule.getPriletDays();
        this.routCh=seazonSchedule.getRoutCh();
        this.routEn=seazonSchedule.getRoutEn();
        this.routRu=seazonSchedule.getRoutRu();
        this.tip_vs=seazonSchedule.getTip_vs();

        this.viletDays=seazonSchedule.getViletDays();

        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, new Locale(locale));
        this.periodStart=df.format(seazonSchedule.getPeriodStart());
        this.periodEnd=df.format(seazonSchedule.getPeriodEnd());

        this.aircompany=seazonSchedule.getAircompany();
        this.img=seazonSchedule.getImg();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm", new Locale(locale));
        this.prilet_time_otpravl= formatter.format(seazonSchedule.getPrilet_time_otpravl());
        this.prilet_time_prib= formatter.format( seazonSchedule.getPrilet_time_prib());
        this.vilet_time_otpravl= formatter.format(seazonSchedule.getVilet_time_otpravl());
        this.vilet_time_prib= formatter.format(seazonSchedule.getVilet_time_prib());
        this.type=seazonSchedule.getTypeReys().name();

    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomerPrilet() {
        return nomerPrilet;
    }

    public void setNomerPrilet(String nomerPrilet) {
        this.nomerPrilet = nomerPrilet;
    }

    public String getNomerVilet() {
        return nomerVilet;
    }

    public void setNomerVilet(String nomerVilet) {
        this.nomerVilet = nomerVilet;
    }

    public String getRoutRu() {
        return routRu;
    }

    public void setRoutRu(String routRu) {
        this.routRu = routRu;
    }

    public String getRoutEn() {
        return routEn;
    }

    public void setRoutEn(String routEn) {
        this.routEn = routEn;
    }

    public String getRoutCh() {
        return routCh;
    }

    public void setRoutCh(String routCh) {
        this.routCh = routCh;
    }

    public String getPeriodStart() {
        return periodStart;
    }

    public void setPeriodStart(String periodStart) {
        this.periodStart = periodStart;
    }

    public String getPeriodEnd() {
        return periodEnd;
    }

    public void setPeriodEnd(String periodEnd) {
        this.periodEnd = periodEnd;
    }

    public String getPriletDays() {
        return priletDays;
    }

    public void setPriletDays(String priletDays) {
        this.priletDays = priletDays;
    }

    public String getPrilet_time_otpravl() {
        return prilet_time_otpravl;
    }

    public void setPrilet_time_otpravl(String prilet_time_otpravl) {
        this.prilet_time_otpravl = prilet_time_otpravl;
    }

    public String getPrilet_time_prib() {
        return prilet_time_prib;
    }

    public void setPrilet_time_prib(String prilet_time_prib) {
        this.prilet_time_prib = prilet_time_prib;
    }

    public String getViletDays() {
        return viletDays;
    }

    public void setViletDays(String viletDays) {
        this.viletDays = viletDays;
    }

    public String getVilet_time_otpravl() {
        return vilet_time_otpravl;
    }

    public void setVilet_time_otpravl(String vilet_time_otpravl) {
        this.vilet_time_otpravl = vilet_time_otpravl;
    }

    public String getVilet_time_prib() {
        return vilet_time_prib;
    }

    public void setVilet_time_prib(String vilet_time_prib) {
        this.vilet_time_prib = vilet_time_prib;
    }

    public String getTip_vs() {
        return tip_vs;
    }

    public void setTip_vs(String tip_vs) {
        this.tip_vs = tip_vs;
    }

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }

    public String getAircompany() {
        return aircompany;
    }

    public void setAircompany(String aircompany) {
        this.aircompany = aircompany;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
