package com.svop.tables.daily_schedule;

import com.svop.tables.Handbooks.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name = "flight_schedule")
public class FlightSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Flight_Schedule_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Flight_Schedule_daily", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Daily daily;


    @Column(name = "Flight_Schedule_time_dep")
    private LocalTime timeDeporture;

    @Column(name = "Flight_Schedule_time_pril")
    private LocalTime timePrilet;


    @Column(name = "Flight_Schedule_day")
    private Date day;



    @Column(name = "Flight_Schedule_Comment")
    private String comment;

    @Column(name = "Flight_Schedule_Status")
    private FlightSheduleStatus status;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Flight_Schedule_Previous", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private FlightSchedule flightSchedulePrevious;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Flight_Schedule_next", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private FlightSchedule flightScheduleNext;


    public FlightSchedule() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Daily getDaily() {
        return daily;
    }

    public void setDaily(Daily daily) {
        this.daily = daily;
    }

    public LocalTime getTimeDeporture() {
        return timeDeporture;
    }

    public void setTimeDeporture(LocalTime timeDeporture) {
        this.timeDeporture = timeDeporture;
    }

    public LocalTime getTimePrilet() {
        return timePrilet;
    }

    public void setTimePrilet(LocalTime timePrilet) {
        this.timePrilet = timePrilet;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public FlightSheduleStatus getStatus() {
        return status;
    }

    public void setStatus(FlightSheduleStatus status) {
        this.status = status;
    }

    public FlightSchedule getFlightSchedulePrevious() {
        return flightSchedulePrevious;
    }

    public void setFlightSchedulePrevious(FlightSchedule flightSchedulePrevious) {
        this.flightSchedulePrevious = flightSchedulePrevious;
    }

    public FlightSchedule getFlightScheduleNext() {
        return flightScheduleNext;
    }

    public void setFlightScheduleNext(FlightSchedule flightScheduleNext) {
        this.flightScheduleNext = flightScheduleNext;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FlightSchedule)) return false;
        FlightSchedule that = (FlightSchedule) o;
        return Objects.equals(getDaily(), that.getDaily());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDaily());
    }
}

