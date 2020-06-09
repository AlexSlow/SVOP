package com.svop.View.DailyScheduleViews;

import java.sql.Date;

public class StoicLanguageDto {
    Date day;
    Integer id;
    String nomer;
    String nomerReys;
    String route;
    String timeVilet;
    String img;
    boolean status;

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public StoicLanguageDto() {
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

    public String getNomerReys() {
        return nomerReys;
    }

    public void setNomerReys(String nomerReys) {
        this.nomerReys = nomerReys;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getTimeVilet() {
        return timeVilet;
    }

    public void setTimeVilet(String timeVilet) {
        this.timeVilet = timeVilet;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
