package com.svop.View.DailyScheduleViews;

import com.svop.tables.daily_schedule.Daily;

import java.sql.Date;

public class DailyScheduleView {
    private String nomer;
    private String rout;
    private String timeDeporture;
    private String timePrilet;
    private String day;
    private String izmen_otmen;
    private String type;
    private String tipVs;
    private String airline;
    private String status;
    private String direction;

    public DailyScheduleView() {
    }
    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNomer() {
        return nomer;
    }

    public void setNomer(String nomer) {
        this.nomer = nomer;
    }

    public String getRout() {
        return rout;
    }

    public void setRout(String rout) {
        this.rout = rout;
    }

    public String getTimeDeporture() {
        return timeDeporture;
    }

    public void setTimeDeporture(String timeDeporture) {
        this.timeDeporture = timeDeporture;
    }

    public String getTimePrilet() {
        return timePrilet;
    }

    public void setTimePrilet(String timePrilet) {
        this.timePrilet = timePrilet;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getIzmen_otmen() {
        return izmen_otmen;
    }

    public void setIzmen_otmen(String izmen_otmen) {
        this.izmen_otmen = izmen_otmen;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTipVs() {
        return tipVs;
    }

    public void setTipVs(String tipVs) {
        this.tipVs = tipVs;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }
}
