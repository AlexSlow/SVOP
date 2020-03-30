package com.svop.tables.Handbooks;

import javax.persistence.*;

@Entity
@Table(name = "sezon")
public class Sezon {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="sezon_id")
    private Integer id;

    @Column(name="sezon_name")
    private String name;
    @Column(name="sezon_begin")
    private String begin;
    @Column(name="sezon_end")
    private String end;

    public Sezon() {
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

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
