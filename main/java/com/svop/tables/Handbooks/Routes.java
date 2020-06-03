package com.svop.tables.Handbooks;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "routs")
public class Routes {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="routs_id")
    private Integer id;

    @Column(name="routs_name")
    private String name;

    @Column(name="routs_prilet")
    private String prilet;
    @Column(name="routs_vilet")
    private String vilet;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false,cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinColumn(name = "routs_airport_arrival", nullable = true)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Airporty arrival;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinColumn(name = "routs_airport_departure", nullable = true)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Airporty deporture;

    public Routes(String name,Airporty arrival,Airporty deporture,String prilet, String vilet) {
        this.name = name;
        this.prilet = prilet;
        this.vilet = vilet;
        this.arrival = arrival;
        this.deporture = deporture;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Airporty getArrival() {
        return arrival;
    }

    public void setArrival(Airporty arrival) {
        this.arrival = arrival;
    }

    public Airporty getDeporture() {
        return deporture;
    }

    public void setDeporture(Airporty deporture) {
        this.deporture = deporture;
    }

    public Routes(){}

    public String getPrilet() {
        return prilet;
    }

    public void setPrilet(String prilet) {
        this.prilet = prilet;
    }

    public String getVilet() {
        return vilet;
    }

    public void setVilet(String vilet) {
        this.vilet = vilet;
    }

    @Override
    public String toString() {
        return "Routes{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Routes)) return false;
        Routes routes = (Routes) o;
        return Objects.equals(getName(), routes.getName()) &&
                Objects.equals(getPrilet(), routes.getPrilet()) &&
                Objects.equals(getVilet(), routes.getVilet()) &&
                Objects.equals(getArrival(), routes.getArrival()) &&
                Objects.equals(getDeporture(), routes.getDeporture());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getPrilet(), getVilet(), getArrival(), getDeporture());
    }
}
