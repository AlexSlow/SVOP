package com.svop.tables.daily_schedule;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.svop.tables.Handbooks.Aircompany;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "registration_stoics")
public class Stoic {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "registration_stoics_id")
    private Integer id;
    @Column(name = "registration_stoics_nomer")
    private String nomer;
    @JsonIgnore
    @Column(name = "registration_stoics_status")
    private StoicStatus status;


    @JsonIgnore
    @ManyToOne(cascade=CascadeType.ALL,fetch = FetchType.LAZY)    //Когда
    @JoinColumn(name = "registration_stoics_reys", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private FlightSchedule flightSchedule;

    public Stoic() {
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

    public FlightSchedule getFlightSchedule() {
        return flightSchedule;
    }

    public void setFlightSchedule(FlightSchedule flightSchedule) {
        this.flightSchedule = flightSchedule;
    }

    public StoicStatus getStatus() {
        return status;
    }

    public void setStatus(StoicStatus status) {
        this.status = status;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Stoic)) return false;
        Stoic stoic = (Stoic) o;
        return Objects.equals(getId(), stoic.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Stoic{" +
                "id=" + id +
                ", nomer='" + nomer + '\'' +
                ", status=" + status +
                ", flightSchedule=" + flightSchedule +
                '}';
    }
}
