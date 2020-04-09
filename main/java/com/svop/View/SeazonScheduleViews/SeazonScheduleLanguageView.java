package com.svop.View.SeazonScheduleViews;

import com.svop.tables.Handbooks.Airline;
import com.svop.tables.SeazonSchedule.SeazonSchedule;

import java.text.DateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class SeazonScheduleLanguageView {
    private Integer id;
    private String nomerPrilet;
    private String nomerVilet;
    private String rout;
    private String periodStart;
    private String periodEnd;
    private String priletDays;
    private String prilet_time_otpravl;
    private String prilet_time_prib;
    private String viletDays;
    private String vilet_time_otpravl;
    private String vilet_time_prib;
    private String aircompany;
    private String img;

    public SeazonScheduleLanguageView() {
    }

    public SeazonScheduleLanguageView(SeazonSchedule seazonSchedule,Integer country)
    {
        if (seazonSchedule==null) return;

        if (country==null)
        {
            country=0;
        }
        this.id=seazonSchedule.getId();
        this.nomerPrilet=seazonSchedule.getNomerPrilet();
        this.nomerVilet=seazonSchedule.getNomerVilet();

        this.priletDays=seazonSchedule.getPriletDays();
        //олучить страну
        Locale locale;
      switch (country)
      {
          case 0:
              this.rout=seazonSchedule.getRoutRu();
              locale=new Locale("ru");
              break;
          case 1:
              this.rout=seazonSchedule.getRoutEn();
              locale=new Locale("en");
              break;
          case 2:
              this.rout=seazonSchedule.getRoutCh();
              locale=new Locale("ch");
              break;
          default:
              this.rout=seazonSchedule.getRoutRu();
              locale=new Locale("ru");
              break;
      }
        this.viletDays=seazonSchedule.getViletDays();
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, locale);
        this.periodStart=df.format(seazonSchedule.getPeriodStart());
        this.periodEnd=df.format(seazonSchedule.getPeriodEnd());
        this.aircompany=seazonSchedule.getAircompany();
        this.img=seazonSchedule.getImg();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm", locale);
        this.prilet_time_otpravl= formatter.format(seazonSchedule.getPrilet_time_otpravl());
        this.prilet_time_prib= formatter.format( seazonSchedule.getPrilet_time_prib());
        this.vilet_time_otpravl= formatter.format(seazonSchedule.getVilet_time_otpravl());
        this.vilet_time_prib= formatter.format(seazonSchedule.getVilet_time_prib());

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

    public String getRout() {
        return rout;
    }

    public void setRoutRu(String rout) {
        this.rout = rout;
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

    @Override
    public String toString() {
        return "SeazonScheduleLanguageView{" +
                "id=" + id +
                ", nomerPrilet='" + nomerPrilet + '\'' +
                ", nomerVilet='" + nomerVilet + '\'' +
                ", rout='" + rout + '\'' +
                ", periodStart='" + periodStart + '\'' +
                ", periodEnd='" + periodEnd + '\'' +
                ", priletDays='" + priletDays + '\'' +
                ", prilet_time_otpravl='" + prilet_time_otpravl + '\'' +
                ", prilet_time_prib='" + prilet_time_prib + '\'' +
                ", viletDays='" + viletDays + '\'' +
                ", vilet_time_otpravl='" + vilet_time_otpravl + '\'' +
                ", vilet_time_prib='" + vilet_time_prib + '\'' +
                ", aircompany='" + aircompany + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
