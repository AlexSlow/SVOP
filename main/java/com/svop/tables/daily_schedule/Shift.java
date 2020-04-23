package com.svop.tables.daily_schedule;

import com.svop.tables.Handbooks.NomerReys;
import com.svop.tables.Handbooks.Routes;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalTime;
@Entity
@Table(name = "shift")
public class Shift {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "shift_id")
    private Integer id;
    @Column(name = "shift_user")
    private String username;
    @Column(name = "shift_day")
    private Date day;
    @Column(name = "shift_status")
    private ShiftStatus status;

    @Column(name = "shift_timeopened")
    private LocalTime timeOpened;

    @Column(name = "shift_timeclosed")
    private LocalTime timeClosed;

    public Shift() {
    }

    public LocalTime getTimeOpened() {
        return timeOpened;
    }

    public void setTimeOpened(LocalTime timeOpened) {
        this.timeOpened = timeOpened;
    }

    public LocalTime getTimeClosed() {
        return timeClosed;
    }

    public void setTimeClosed(LocalTime timeClosed) {
        this.timeClosed = timeClosed;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public ShiftStatus getStatus() {
        return status;
    }

    public void setStatus(ShiftStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Shift{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", day=" + day +
                ", status=" + status +
                '}';
    }

    public Shift(String username, Date day, ShiftStatus status) {
        this.username = username;
        this.day = day;
        this.status = status;
    }
}
