package com.svop.tables.Handbooks;

import com.svop.tables.Auditable;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Objects;
@Audited
@Entity
@Table(name = "reysy_nomer")
public class NomerReys extends Auditable<String> {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="reysy_nomer_id")
    private Integer id;

    @Column(name="reysy_nomer")
    private String nomer;

    @Column(name="reysy_nomer_type")
    private ReysyNomerType type;
    @ManyToOne(cascade={CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.LAZY)    //Когда
    @JoinColumn(name = "reysy_nomer_aircompany_id", nullable = true)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Aircompany aircompany;
    public NomerReys(){}
    public NomerReys(String nomer, ReysyNomerType type, Aircompany aircompany) {
        this.nomer = nomer;
        this.type = type;
        this.aircompany = aircompany;
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

    public ReysyNomerType getType() {
        return type;
    }

    public void setType(ReysyNomerType type) {
        this.type = type;
    }

    public Aircompany getAircompany() {
        return aircompany;
    }

    public void setAircompany(Aircompany aircompany) {
        this.aircompany = aircompany;
    }

    @Override
    public String toString() {
        return "NomerReys{" +
                "id=" + id +
                ", nomer='" + nomer + '\'' +
                ", type=" + type +
                ", aircompany=" + aircompany.getId() +
                ", createdBy=" + createdBy +
                ", creationDate=" + creationDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NomerReys)) return false;
        NomerReys nomerReys = (NomerReys) o;
        return Objects.equals(getNomer(), nomerReys.getNomer()) &&
                getType() == nomerReys.getType() &&
                Objects.equals(getAircompany(), nomerReys.getAircompany());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNomer(), getType(), getAircompany());
    }
}
