package com.svop.tables.daily_schedule;

import com.svop.tables.Handbooks.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name = "daily_schedule")
public class Daily {
        @Id
        @GeneratedValue(strategy= GenerationType.AUTO)
        @Column(name="daily_schedule_id")
        private Integer id;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "old_id", nullable = false)
        @OnDelete(action = OnDeleteAction.CASCADE)
        private Reysy reys;

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "daily_schedule_nomer", nullable = false)
        @OnDelete(action = OnDeleteAction.CASCADE)
        private NomerReys nomer;

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "daily_schedule_route", nullable = false)
        @OnDelete(action = OnDeleteAction.CASCADE)
        private Routes rout;

        @Column(name="daily_schedule_time_dep")
        private LocalTime timeDeporture;

        @Column(name="daily_schedule_time_pril")
        private LocalTime timePrilet;


        @Column(name="daily_schedule_day")
        private Date day;


        @Column(name="daily_schedule_izm")
        private ReysyStatus izmenOmen;

        @Column(name="daily_schedule_tip")
        private TypeReys type;


        @Column(name="daily_schedule_tip_vs")
        private String tipVs;
    @Column(name="daily_schedule_airline")
    private Airline airline;

    @Column(name="daily_schedule_direction")
    private DailyDirection direction;

        public Daily() {
        }

    public DailyDirection getDirection() {
        return direction;
    }

    public void setDirection(DailyDirection direction) {
        this.direction = direction;
    }

    public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

    public Reysy getReys() {
        return reys;
    }

    public void setReys(Reysy reys) {
        this.reys = reys;
    }

    public NomerReys getNomer() {
            return nomer;
        }

        public void setNomer(NomerReys nomer) {
            this.nomer = nomer;
        }

        public Routes getRout() {
            return rout;
        }

        public void setRout(Routes rout) {
            this.rout = rout;
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

    public ReysyStatus getIzmenOmen() {
        return izmenOmen;
    }

    public void setIzmenOmen(ReysyStatus izmenOmen) {
        this.izmenOmen = izmenOmen;
    }

    public TypeReys getType() {
            return type;
        }

        public void setType(TypeReys type) {
            this.type = type;
        }

        public String getTipVs() {
            return tipVs;
        }

        public void setTipVs(String tipVs) {
            this.tipVs = tipVs;
        }

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }


/*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Daily)) return false;
        Daily daily = (Daily) o;
        return Objects.equals(getReys(), daily.getReys()) &&
                Objects.equals(getNomer(), daily.getNomer()) &&
                Objects.equals(getRout(), daily.getRout()) &&
                Objects.equals(getTimeDeporture(), daily.getTimeDeporture()) &&
                Objects.equals(getTimePrilet(), daily.getTimePrilet()) &&
                Objects.equals(getDay(), daily.getDay()) &&
                Objects.equals(getTipVs(), daily.getTipVs());
    }
*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Daily)) return false;
        Daily daily = (Daily) o;
        return Objects.equals(getReys(), daily.getReys()) &&
                Objects.equals(getNomer(), daily.getNomer()) &&
                Objects.equals(getRout(), daily.getRout()) &&
                Objects.equals(getTimeDeporture(), daily.getTimeDeporture()) &&
                Objects.equals(getTimePrilet(), daily.getTimePrilet()) &&
                Objects.equals(getDay(), daily.getDay()) &&
                getIzmenOmen() == daily.getIzmenOmen() &&
                getType() == daily.getType() &&
                Objects.equals(getTipVs(), daily.getTipVs()) &&
                getAirline() == daily.getAirline();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getReys(), getNomer(), getRout(), getTimeDeporture(), getTimePrilet(), getDay(), getIzmenOmen(), getType(), getTipVs(), getAirline());
    }

    @Override
    public String toString() {
        return "Daily{" +
                "id=" + id +
                ", reys=" + reys +
                ", nomer=" + nomer +
                ", rout=" + rout +
                ", timeDeporture=" + timeDeporture +
                ", timePrilet=" + timePrilet +
                ", day=" + day +
                ", izmenOmen=" + izmenOmen +
                ", type=" + type +
                ", tipVs='" + tipVs + '\'' +
                ", airline=" + airline +
                ", direction=" + direction +
                '}';
    }
}
