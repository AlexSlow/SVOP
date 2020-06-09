package com.svop.View.DailyScheduleViews;

import java.sql.Date;

public class StoicDto {
    Date day;
    Integer id;
    String nomer;
    String nomerReys;
    String routeRu;
    String routeEn;
    String routeCh;
    String timeViletRu;
    String img;
    String imgLittle;
    boolean status;

    public String getImgLittle() {
        return imgLittle;
    }

    public void setImgLittle(String imgLittle) {
        this.imgLittle = imgLittle;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public StoicDto() {
        nomerReys="Не задан";
    }

    public String getNomerReys() {
        return nomerReys;
    }

    public void setNomerReys(String nomerReys) {
        this.nomerReys = nomerReys;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomer() {
        return nomer;
    }

    public void setNomer(String nomer) {
        this.nomer = nomer;
    }

    public String getRouteRu() {
        return routeRu;
    }

    public void setRouteRu(String routeRu) {
        this.routeRu = routeRu;
    }

    public String getRouteEn() {
        return routeEn;
    }

    public void setRouteEn(String routeEn) {
        this.routeEn = routeEn;
    }

    public String getRouteCh() {
        return routeCh;
    }

    public void setRouteCh(String routeCh) {
        this.routeCh = routeCh;
    }

    public String getTimeViletRu() {
        return timeViletRu;
    }

    public void setTimeViletRu(String timeViletRu) {
        this.timeViletRu = timeViletRu;
    }


    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "StoicDto{" +
                "id=" + id +
                ", nomer='" + nomer + '\'' +
                ", nomerReys='" + nomerReys + '\'' +
                ", routeRu='" + routeRu + '\'' +
                ", routeEn='" + routeEn + '\'' +
                ", routeCh='" + routeCh + '\'' +
                ", timeViletRu='" + timeViletRu + '\'' +
                ", img='" + img + '\'' +
                ", status=" + status +
                '}';
    }
}
