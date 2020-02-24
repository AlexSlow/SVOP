package com.svop.tables.Handbooks;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "airporty")
public class Airporty implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="airporty_id")
    private Integer id;

    @Column(name="airporty_name_rus")
    private String NameRu;
    @Column(name="airporty_name_eng")
    private String NameEng;
    @Column(name="airporty_name_china")
    private String NameCh;
    private String GMT;
    @Column(name="airporty_ICAO")
    private String ICAO;
    @Column(name="airporty_IATA")
    private String IATA;

    public Airporty(){}

    public Airporty(String nameRu, String nameEng, String nameCh, String GMT, String ICAO, String IATA) {
        NameRu = nameRu;
        NameEng = nameEng;
        NameCh = nameCh;
        this.GMT = GMT;
        this.ICAO = ICAO;
        this.IATA = IATA;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNameRu(String nameRu) {
        NameRu = nameRu;
    }

    public void setNameEng(String nameEng) {
        NameEng = nameEng;
    }

    public void setNameCh(String nameCh) {
        NameCh = nameCh;
    }

    public void setGMT(String GMT) {
        this.GMT = GMT;
    }

    public void setICAO(String ICAO) {
        this.ICAO = ICAO;
    }

    public void setIATA(String IATA) {
        this.IATA = IATA;
    }

    public Integer getId() {
        return id;
    }

    public String getNameRu() {
        return NameRu;
    }

    public String getNameEng() {
        return NameEng;
    }

    public String getNameCh() {
        return NameCh;
    }

    public String getGMT() {
        return GMT;
    }

    public String getICAO() {
        return ICAO;
    }

    public String getIATA() {
        return IATA;
    }
}
