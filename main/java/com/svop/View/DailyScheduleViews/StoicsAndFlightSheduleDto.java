package com.svop.View.DailyScheduleViews;

public class StoicsAndFlightSheduleDto {
    private FlightScheduleView flightScheduleView;
    private boolean bind;
    private String nomerStoic;
    private Integer idStoic;

    public StoicsAndFlightSheduleDto() {

    }

    public StoicsAndFlightSheduleDto(FlightScheduleView flightScheduleView, boolean bind, String nomerStoic, Integer idStoic) {
        this.flightScheduleView = flightScheduleView;
        this.bind = bind;
        this.nomerStoic = nomerStoic;
        this.idStoic = idStoic;
    }

    public StoicsAndFlightSheduleDto(FlightScheduleView flightScheduleView, boolean bind) {
        this.flightScheduleView = flightScheduleView;
        this.bind = bind;
    }

    public FlightScheduleView getFlightScheduleView() {
        return flightScheduleView;
    }

    public void setFlightScheduleView(FlightScheduleView flightScheduleView) {
        this.flightScheduleView = flightScheduleView;
    }

    public boolean isBind() {
        return bind;
    }

    public void setBind(boolean bind) {
        this.bind = bind;
    }

    public String getNomerStoic() {
        return nomerStoic;
    }

    public void setNomerStoic(String nomerStoic) {
        this.nomerStoic = nomerStoic;
    }

    public Integer getIdStoic() {
        return idStoic;
    }

    public void setIdStoic(Integer idStoic) {
        this.idStoic = idStoic;
    }
}
