package com.svop.tables.handbooks;

import javax.persistence.*;

@Entity
@Table(name = "airporty")
public class Airporty {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer airporty_id;

    private String airporty_name_rus;
    private String airporty_name_eng;
    private String airporty_name_china;
    private String GMT;
    private String airporty_ICAO;
    private String airporty_IATA;

    public Airporty(){}
    public Airporty(String airporty_name_rus, String airporty_name_eng, String airporty_name_china, String GMT, String airporty_ICAO, String airporty_IATA) {
        this.airporty_name_rus = airporty_name_rus;
        this.airporty_name_eng = airporty_name_eng;
        this.airporty_name_china = airporty_name_china;
        this.GMT = GMT;
        this.airporty_ICAO = airporty_ICAO;
        this.airporty_IATA = airporty_IATA;
    }

    public Integer getAirporty_id() {
        return airporty_id;
    }

    public void setAirporty_id(Integer airporty_id) {
        this.airporty_id = airporty_id;
    }

    public String getAirporty_name_rus() {
        return airporty_name_rus;
    }

    public void setAirporty_name_rus(String airporty_name_rus) {
        this.airporty_name_rus = airporty_name_rus;
    }

    public String getAirporty_name_eng() {
        return airporty_name_eng;
    }

    public void setAirporty_name_eng(String airporty_name_eng) {
        this.airporty_name_eng = airporty_name_eng;
    }

    public String getAirporty_name_china() {
        return airporty_name_china;
    }

    public void setAirporty_name_china(String airporty_name_china) {
        this.airporty_name_china = airporty_name_china;
    }

    public String getGMT() {
        return GMT;
    }

    public void setGMT(String GMT) {
        this.GMT = GMT;
    }

    public String getAirporty_ICAO() {
        return airporty_ICAO;
    }

    public void setAirporty_ICAO(String airporty_ICAO) {
        this.airporty_ICAO = airporty_ICAO;
    }

    public String getAirporty_IATA() {
        return airporty_IATA;
    }

    public void setAirporty_IATA(String airporty_IATA) {
        this.airporty_IATA = airporty_IATA;
    }
}
