package com.svop.tables.journal;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "SeazonJournalProcedure")
public class SeazonJournalProcedure {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="SeazonJournalProcedure_id")
    private Integer id;

    @Column(name="SeazonJournalProcedure_username")
    private String name;
    @Column(name="SeazonJournalProcedure_date")
    private Date date;

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

    public Date getDate() {
        return date;
    }

    public void setBegin(Date begin) {
        this.date = begin;
    }

    public SeazonJournalProcedure() {
    }

    public SeazonJournalProcedure(String name, Date date) {
        this.name = name;
        this.date = date;
    }
}
