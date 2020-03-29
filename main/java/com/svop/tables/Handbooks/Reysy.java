package com.svop.tables.Handbooks;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;


@Entity
@Table(name = "reysy")
public class Reysy {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="reysy_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reysy_nomer_prilet", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private NomerReys nomer_prilet;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reysy_nomer_vilet", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private NomerReys nomer_vilet;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reysy_rout", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Routes rout;

    @Column(name="reysy_period_start")
    private Date period_start;

    @Column(name="reysy_period_end")
    private Date period_end;

    @Column(name="reysy_prilet_days")
    private String prilet_days;

    @Column(name="reysy_prilet_time_otpravl")
    private LocalTime prilet_time_otpravl;

    @Column(name="reysy_prilet_time_prib")
    private LocalTime prilet_time_prib;


    @Column(name="reysy_vilet_days")
    private String vilet_days;

    @Column(name="reysy_vilet_time_otpravl")
    private LocalTime vilet_time_otpravl;

    @Column(name="reysy_vilet_time_prib")
    private LocalTime vilet_time_prib;

    @Column(name="reysy_tip_vs")
    private String tip_vs;

    @Column(name="reysy_izmen_otmen")
    private ReysyStatus izmen_otmen;

    @Column(name="reysy_osnovanie_izmen_otmen")
    private String osnovanie_izmen_otmen;

    @Column(name="reysy_tip")
    private TypeReys type;

    @Column(name="reysy_airline")
    private Airline airline;

    public Reysy() {
    }

    public Reysy(NomerReys nomer_prilet, NomerReys nomer_vilet, Routes rout, Date period_start, Date period_end, String prilet_days, LocalTime prilet_time_otpravl, LocalTime prilet_time_prib, String vilet_days, LocalTime vilet_time_otpravl, LocalTime vilet_time_prib, String tip_vs, ReysyStatus izmen_otmen, String osnovanie_izmen_otmen, TypeReys type, Airline airline) {
        this.nomer_prilet = nomer_prilet;
        this.nomer_vilet = nomer_vilet;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public NomerReys getNomer_prilet() {
        return nomer_prilet;
    }

    public void setNomer_prilet(NomerReys nomer_prilet) {
        this.nomer_prilet = nomer_prilet;
    }

    public NomerReys getNomer_vilet() {
        return nomer_vilet;
    }

    public void setNomer_vilet(NomerReys nomer_vilet) {
        this.nomer_vilet = nomer_vilet;
    }

    public Routes getRout() {
        return rout;
    }

    public void setRout(Routes rout) {
        this.rout = rout;
    }

    public Date getPeriod_start() {
        return period_start;
    }

    public void setPeriod_start(Date period_start) {
        this.period_start = period_start;
    }

    public Date getPeriod_end() {
        return period_end;
    }

    public void setPeriod_end(Date period_end) {
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

    @Override
    public String toString() {
        return "Reysy{" +
                "id=" + id +
                ", nomer_prilet=" + nomer_prilet +
                ", nomer_vilet=" + nomer_vilet +
                ", rout=" + rout +
                ", period_start=" + period_start +
                ", period_end=" + period_end +
                ", prilet_days='" + prilet_days + '\'' +
                ", prilet_time_otpravl=" + prilet_time_otpravl +
                ", prilet_time_prib=" + prilet_time_prib +
                ", vilet_days='" + vilet_days + '\'' +
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
