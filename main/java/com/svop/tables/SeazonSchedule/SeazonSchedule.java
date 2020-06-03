package com.svop.tables.SeazonSchedule;

import com.svop.tables.Handbooks.Airline;
import com.svop.tables.Handbooks.ReysyNomerType;
import com.svop.tables.Handbooks.ReysyStatus;
import com.svop.tables.Handbooks.TypeReys;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalTime;

@Entity
@Table(name = "output_sezon")
public class SeazonSchedule {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="output_sezon_id")
    private Integer id;

    @Column(name="output_sezon_nomer_prilet")
    private String nomerPrilet;

    @Column(name="output_sezon_nomer_vilet")
    private String nomerVilet;


    @Column(name="output_sezon_marshrut_name_ru")
    private String routRu;

    @Column(name="output_sezon_marshrut_name_en")
    private String routEn;

    @Column(name="output_sezon_marshrut_name_ch")
    private String routCh;

    @Column(name="output_sezon_period_start")
    private Date periodStart;

    @Column(name="output_sezon_period_end")
    private Date periodEnd;

    @Column(name="output_sezon_prilet_days")
    private String priletDays;

    @Column(name="output_sezon_prilet_time_otpravl")
    private LocalTime prilet_time_otpravl;

    @Column(name="output_sezon_prilet_time_prib")
    private LocalTime prilet_time_prib;


    @Column(name="output_sezon_vilet_days")
    private String viletDays;

    @Column(name="output_sezon_vilet_time_otpravl")
    private LocalTime vilet_time_otpravl;

    @Column(name="output_sezon_vilet_time_prib")
    private LocalTime vilet_time_prib;



    @Column(name="output_sezon_tip_vs")
    private String tip_vs;
    @Column(name="output_sezon_airline")
    private Airline airline;

    @Column(name="output_sezon_aircompany")
    private String aircompany;

    @Column(name="output_sezon_aircompany_img")
    private String img;


    @Column(name="output_sezon_type")
    private TypeReys typeReys;

    public SeazonSchedule() {
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

    public Date getPeriodStart() {
        return periodStart;
    }

    public void setPeriodStart(Date periodStart) {
        this.periodStart = periodStart;
    }

    public Date getPeriodEnd() {
        return periodEnd;
    }

    public void setPeriodEnd(Date periodEnd) {
        this.periodEnd = periodEnd;
    }

    public String getPriletDays() {
        return priletDays;
    }

    public void setPriletDays(String priletDays) {
        this.priletDays = priletDays;
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

    public String getViletDays() {
        return viletDays;
    }

    public void setViletDays(String viletDays) {
        this.viletDays = viletDays;
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


    public TypeReys getTypeReys() {
        return typeReys;
    }

    public void setTypeReys(TypeReys typeReys) {
        this.typeReys = typeReys;
    }
}
