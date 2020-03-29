package com.svop.View;

import com.svop.tables.Handbooks.*;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Locale;

public class ReysyListView {
    private Integer id;
    private String nomer_prilet;
    private String nomer_vilet;
    private String rout;
    private String period_start;
    private String period_end;
    private String prilet_days;
    private LocalTime prilet_time_otpravl;
    private LocalTime prilet_time_prib;
    private String vilet_days;
    private LocalTime vilet_time_otpravl;
    private LocalTime vilet_time_prib;
    private String tip_vs;
    private ReysyStatus izmen_otmen;
    //private TypeReys type;
    private Airline airline;

    public ReysyListView() {
    }



    public Integer getId() {
        return id;
    }

    public ReysyListView(Reysy reys,String locale)
    {
        if (locale==null)locale="ru";
        this.id=reys.getId();
        this.nomer_prilet=reys.getNomer_prilet().getNomer();
        this.nomer_vilet=reys.getNomer_vilet().getNomer();

        this.airline=reys.getAirline();
        this.prilet_days=reys.getPrilet_days();
        this.vilet_days=reys.getVilet_days();
        this.izmen_otmen=reys.getIzmen_otmen();
       // this.type=reys.getType();
        this.tip_vs=reys.getTip_vs();



        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, new Locale(locale));
        this.period_start = df.format(reys.getPeriod_start());
        this.period_end = df.format(reys.getPeriod_end());

        //System.out.println( df.format(reys.getPrilet_time_otpravl()));
        this.prilet_time_otpravl=reys.getPrilet_time_otpravl();
        this.prilet_time_prib=reys.getPrilet_time_prib();
        this.vilet_time_otpravl=reys.getVilet_time_otpravl();
        this.vilet_time_prib=reys.getVilet_time_prib();

    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomer_prilet() {
        return nomer_prilet;
    }

    public void setNomer_prilet(String nomer_prilet) {
        this.nomer_prilet = nomer_prilet;
    }

    public String getNomer_vilet() {
        return nomer_vilet;
    }

    public void setNomer_vilet(String nomer_vilet) {
        this.nomer_vilet = nomer_vilet;
    }

    public String getRout() {
        return rout;
    }

    public void setRout(String rout) {
        this.rout = rout;
    }

    public String getPeriod_start() {
        return period_start;
    }

    public void setPeriod_start(String period_start) {
        this.period_start = period_start;
    }

    public String getPeriod_end() {
        return period_end;
    }

    public void setPeriod_end(String period_end) {
        this.period_end = period_end;
    }

    public String getPrilet_days() {
        return prilet_days;
    }

    public void setPrilet_days(String prilet_days) {
        this.prilet_days = prilet_days;
    }

    public LocalTime getPrilet_time_otpravl() {
        return prilet_time_otpravl;
    }

    public void setPrilet_time_otpravl(LocalTime prilet_time_otpravl) {
        this.prilet_time_otpravl = prilet_time_otpravl;
    }

    public LocalTime getPrilet_time_prib() {
        return prilet_time_prib;
    }

    public void setPrilet_time_prib(LocalTime prilet_time_prib) {
        this.prilet_time_prib = prilet_time_prib;
    }

    public String getVilet_days() {
        return vilet_days;
    }

    public void setVilet_days(String vilet_days) {
        this.vilet_days = vilet_days;
    }

    public LocalTime getVilet_time_otpravl() {
        return vilet_time_otpravl;
    }

    public void setVilet_time_otpravl(LocalTime vilet_time_otpravl) {
        this.vilet_time_otpravl = vilet_time_otpravl;
    }

    public LocalTime getVilet_time_prib() {
        return vilet_time_prib;
    }

    public void setVilet_time_prib(LocalTime vilet_time_prib) {
        this.vilet_time_prib = vilet_time_prib;
    }

    public String getTip_vs() {
        return tip_vs;
    }

    public void setTip_vs(String tip_vs) {
        this.tip_vs = tip_vs;
    }

    public ReysyStatus getIzmen_otmen() {
        return izmen_otmen;
    }

    public void setIzmen_otmen(ReysyStatus izmen_otmen) {
        this.izmen_otmen = izmen_otmen;
    }

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }
}
