package com.svop.View.DailyScheduleViews;

public class FlightScheduleView {
    private Integer id;
    private String nomer;
    private String rout;
    private String timeDeporture;
    private String timePrilet;
    private String day;
    private String newDay;
    private String type;
    private String tipVs;
    private String comment;
    private String status;
    private String direction;

    public FlightScheduleView() {
        comment=new String();
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNewDay() {
        return newDay;
    }

    public void setNewDay(String newDay) {
        this.newDay = newDay;
    }

    @Override
    public String toString() {
        return "FlightScheduleView{" +
                "id=" + id +
                ", nomer='" + nomer + '\'' +
                ", rout='" + rout + '\'' +
                ", timeDeporture='" + timeDeporture + '\'' +
                ", timePrilet='" + timePrilet + '\'' +
                ", day='" + day + '\'' +
                ", newDay='" + newDay + '\'' +
                ", type='" + type + '\'' +
                ", tipVs='" + tipVs + '\'' +
                ", comment='" + comment + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
