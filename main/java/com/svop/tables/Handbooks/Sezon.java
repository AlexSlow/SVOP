package com.svop.tables.Handbooks;

import com.svop.tables.Auditable;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.sql.Date;

@Audited
@Entity
@Table(name = "sezon")
public class Sezon extends Auditable<String> {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="sezon_id")
    private Integer id;

    @Column(name="sezon_name")
    private String name;
    @Column(name="sezon_begin")
    private Date begin;
    @Column(name="sezon_end")
    private Date end;

    public Sezon() {
    }

    @Override
    public String toString() {
        return "Sezon{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", begin=" + begin +
                ", end=" + end +
                ", createdBy=" + createdBy +
                ", creationDate=" + creationDate +
                '}';
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

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}
