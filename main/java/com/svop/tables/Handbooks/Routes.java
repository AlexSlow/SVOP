package com.svop.tables.Handbooks;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "routs_airport_arrival", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Airporty arrival;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "routs_airport_departure", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
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
}
