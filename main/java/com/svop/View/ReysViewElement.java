package com.svop.View;

import com.svop.tables.Handbooks.Airline;
import com.svop.tables.Handbooks.Reysy;
import com.svop.tables.Handbooks.ReysyStatus;
import com.svop.tables.Handbooks.TypeReys;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReysViewElement {

    private Integer id;
    private Integer nomer_prilet_id;
    private Integer nomer_vilet_id;
    private Integer rout;
    private String period_start;
    private String period_end;
    private List<Integer> prilet_days;
    private LocalTime prilet_time_otpravl;
    private LocalTime prilet_time_prib;
    private List<Integer> vilet_days;
    private LocalTime vilet_time_otpravl;
    private LocalTime vilet_time_prib;
    private String tip_vs;
    private String izmen_otmen;
    private String osnovanie_izmen_otmen;
    private TypeReys type;
    private Airline airline;

    public ReysViewElement() {
        this.prilet_days = Arrays.asList(0,0,0,0,0,0,0);
        this.vilet_days = Arrays.asList(0,0,0,0,0,0,0);
    }

    public ReysViewElement(Integer id, Integer nomer_prilet_id, Integer nomer_vilet_id, Integer rout, String period_start,
                           String period_end, List<Integer> prilet_days, LocalTime prilet_time_otpravl,
                           LocalTime prilet_time_prib, List<Integer> vilet_days, LocalTime vilet_time_otpravl,
                           LocalTime vilet_time_prib, String tip_vs, String izmen_otmen, String osnovanie_izmen_otmen,
                           TypeReys type, Airline airline) {
        this.id = id;
        this.nomer_prilet_id = nomer_prilet_id;
        this.nomer_vilet_id = nomer_vilet_id;
        this.rout = rout;
        this.period_start = period_start;
        this.period_end = period_end;
        this.prilet_days = prilet_days;
        this.prilet_time_otpravl = prilet_time_otpravl;
        this.prilet_time_prib = prilet_time_prib;
        this.vilet_days = vilet_days;
        this.vilet_time_otpravl = vilet_time_otpravl;
        this.vilet_time_prib = vilet_time_prib;
        this.tip_vs = tip_vs;
        this.izmen_otmen = izmen_otmen;
        this.osnovanie_izmen_otmen = osnovanie_izmen_otmen;
        this.type = type;
        this.airline = airline;
    }

    public ReysViewElement(Reysy reys)
    {
        this.id = reys.getId();
        this.nomer_prilet_id = reys.getNomer_prilet().getId();
        this.nomer_vilet_id = reys.getNomer_vilet().getId();
        this.rout = reys.getRout().getId();
        this.period_start = reys.getPeriod_start().toString();
       // System.out.println(this.period_start);
        this.period_end = reys.getPeriod_end().toString();
        //Получить дни прилета
        this.prilet_days=Arrays.asList(0,0,0,0,0,0,0);
        String [] prilet_days=reys.getPrilet_days().split("/");
        //Arrays.stream(deys).forEach(this.prilet_days.add(x->Integer.parseInt(x)));
        for(int i=0;i<prilet_days.length;i++){this.prilet_days.set(Integer.parseInt(prilet_days[i]),1);}

        this.prilet_time_otpravl = reys.getPrilet_time_otpravl();
        this.prilet_time_prib = reys.getPrilet_time_prib();

        //Преобразовать дни вылета
        this.vilet_days=Arrays.asList(0,0,0,0,0,0,0);
        String [] vilet_days=reys.getVilet_days().split("/");
        for(int i=0;i<vilet_days.length;i++){this.vilet_days.set(Integer.parseInt(vilet_days[i]),1);}

        this.vilet_time_otpravl = reys.getVilet_time_otpravl();
        this.vilet_time_prib = reys.getVilet_time_prib();
        this.tip_vs = reys.getTip_vs();
        //Reysy статус
        if (reys.getIzmen_otmen()==ReysyStatus.Canceled){ this.izmen_otmen ="on";}else{this.izmen_otmen ="";}

        this.osnovanie_izmen_otmen = reys.getOsnovanie_izmen_otmen();
        this.type = reys.getType();
        this.airline = reys.getAirline();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNomer_prilet_id() {
        return nomer_prilet_id;
    }

    public void setNomer_prilet_id(Integer nomer_prilet_id) {
        this.nomer_prilet_id = nomer_prilet_id;
    }

    public Integer getNomer_vilet_id() {
        return nomer_vilet_id;
    }

    public void setNomer_vilet_id(Integer nomer_vilet_id) {
        this.nomer_vilet_id = nomer_vilet_id;
    }

    public Integer getRout() {
        return rout;
    }

    public void setRout(Integer rout) {
        this.rout = rout;
    }

    public String getPeriod_start() {
        return period_start;
    }

    public void setPeriod_start(String period_start) {
        //System.out.println(period_start);
        //this.period_start = LocalDate.parse(period_start);
        this.period_start=period_start;
    }

    public String getPeriod_end() {
        return period_end;
    }

    public void setPeriod_end(String period_end) {
        this.period_end=period_end;
       // this.period_end = LocalDate.parse(period_end);
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

    public String getIzmen_otmen() {
        return izmen_otmen;
    }

    public void setIzmen_otmen(String izmen_otmen) {
        this.izmen_otmen = izmen_otmen;
    }

    public String getOsnovanie_izmen_otmen() {
        return osnovanie_izmen_otmen;
    }

    public void setOsnovanie_izmen_otmen(String osnovanie_izmen_otmen) {
        this.osnovanie_izmen_otmen = osnovanie_izmen_otmen;
    }

    public TypeReys getType() {
        return type;
    }

    public void setType(TypeReys type) {
        this.type = type;
    }

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }


    public List<Integer> getPrilet_days() {
        return prilet_days;
    }

    public void setPrilet_days(List<Integer> prilet_days) {
        this.prilet_days = prilet_days;
    }

    public List<Integer> getVilet_days() {
        return vilet_days;
    }

    public void setVilet_days(List<Integer> vilet_days) {
        this.vilet_days = vilet_days;
    }

    @Override
    public String toString() {
        return "ReysViewElement{" +
                "id=" + id +
                ", nomer_prilet_id=" + nomer_prilet_id +
                ", nomer_vilet_id=" + nomer_vilet_id +
                ", rout=" + rout +
                ", period_start=" + period_start +
                ", period_end=" + period_end +
                ", prilet_days=" + prilet_days +
                ", prilet_time_otpravl=" + prilet_time_otpravl +
                ", prilet_time_prib=" + prilet_time_prib +
                ", vilet_days=" + vilet_days+
                ", vilet_time_otpravl=" + vilet_time_otpravl +
                ", vilet_time_prib=" + vilet_time_prib +
                ", tip_vs='" + tip_vs + '\'' +
                ", izmen_otmen=" + izmen_otmen +
                ", osnovanie_izmen_otmen='" + osnovanie_izmen_otmen + '\'' +
                ", type=" + type +
                ", airline=" + airline +
                '}';
    }
}
