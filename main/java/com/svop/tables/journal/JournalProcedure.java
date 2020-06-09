package com.svop.tables.journal;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "JournalProcedure")
public class JournalProcedure {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="SeazonJournalProcedure_id")
    private Integer id;

    @Column(name="SeazonJournalProcedure_username")
    private String name;
    @Column(name="SeazonJournalProcedure_date")
    private Date date;
    @Column(name="SeazonJournalProcedure_type")
    private TypeProcedure typeProcedure;


    public void setDate(Date date) {
        this.date = date;
    }

    public TypeProcedure getTypeProcedure() {
        return typeProcedure;
    }

    public void setTypeProcedure(TypeProcedure typeProcedure) {
        this.typeProcedure = typeProcedure;
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

    public Date getDate() {
        return date;
    }
    public JournalProcedure() {
    }

    public JournalProcedure(String name, Date date,TypeProcedure typeProcedure) {
        this.name = name;
        this.date = date;
        this.typeProcedure=typeProcedure;
    }
}
