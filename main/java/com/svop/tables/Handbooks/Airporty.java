package com.svop.tables.Handbooks;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "airporty")
public class Airporty implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="airporty_id")
    private Integer id;

    @Column(name="airporty_name_rus")
    private String nameRu;
    @Column(name="airporty_name_eng")
    private String nameEng;
    @Column(name="airporty_name_china")
    private String nameCh;
    private String gmt;
    @Column(name="airporty_ICAO")
    private String icao;
    @Column(name="airporty_IATA")
    private String iata;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameRu() {
        return nameRu;
    }

    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }

    public String getNameEng() {
        return nameEng;
    }

    public void setNameEng(String nameEng) {
        this.nameEng = nameEng;
    }

    public String getNameCh() {
        return nameCh;
    }

    public void setNameCh(String nameCh) {
        this.nameCh = nameCh;
    }

    public String getGmt() {
        return gmt;
    }

    public void setGmt(String gmt) {
        this.gmt = gmt;
    }

    public String getIcao() {
        return icao;
    }

    public void setIcao(String icao) {
        this.icao = icao;
    }

    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }
    public Airporty(){}
    public Airporty(String nameRu, String nameEng, String nameCh, String gmt, String icao, String iata) {
        this.nameRu = nameRu;
        this.nameEng = nameEng;
        this.nameCh = nameCh;
        this.gmt = gmt;
        this.icao = icao;
        this.iata = iata;
    }


    @Override
    public String toString() {
        return "Airporty{" +
                "id=" + id +
                ", nameRu='" + nameRu + '\'' +
                ", nameEng='" + nameEng + '\'' +
                ", nameCh='" + nameCh + '\'' +
                ", gmt='" + gmt + '\'' +
                ", icao='" + icao + '\'' +
                ", iata='" + iata + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Airporty)) return false;
        Airporty airporty = (Airporty) o;
        return Objects.equals(getNameRu(), airporty.getNameRu()) &&
                Objects.equals(getNameEng(), airporty.getNameEng()) &&
                Objects.equals(getNameCh(), airporty.getNameCh()) &&
                Objects.equals(getGmt(), airporty.getGmt()) &&
                Objects.equals(getIcao(), airporty.getIcao()) &&
                Objects.equals(getIata(), airporty.getIata());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNameRu(), getNameEng(), getNameCh(), getGmt(), getIcao(), getIata());
    }
}
