package com.svop.message.flightShedule;

import java.sql.Date;
import java.time.LocalTime;

public class FlightScheduleMove {
    private Integer id;
    private Date day;
    private LocalTime deporture;
    private LocalTime prilet;
    private String comment;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public FlightScheduleMove() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public LocalTime getDeporture() {
        return deporture;
    }

    public void setDeporture(LocalTime deporture) {
        this.deporture = deporture;
    }

    public LocalTime getPrilet() {
        return prilet;
    }

    public void setPrilet(LocalTime prilet) {
        this.prilet = prilet;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "FlightScheduleMove{" +
                "id=" + id +
                ", day=" + day +
                ", deporture=" + deporture +
                ", prilet=" + prilet +
                ", comment='" + comment + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
