package com.svop.tables.Handbooks;

import com.svop.View.AircompanyView;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "aircompany")
public class Aircompany {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="aircompany_id")
    private Integer id;

    @Column(name="aircompany_long_name")
    private String nameLong;

    @Column(name="aircompany_short_name")
    private String nameShort;

    @OneToMany(mappedBy="aircompany")
    private Set<NomerReys> nomers;

    @Column(name="aircompany_logo")
    private String logo;
    public Aircompany(){}
    public Aircompany(String nameLong, String nameShort, String logo) {
        this.nameLong = nameLong;
        this.nameShort = nameShort;
        this.logo = logo;
    }

    public Aircompany(String nameLong, String nameShort, Set<NomerReys> nomers, String logo) {
        this.nameLong = nameLong;
        this.nameShort = nameShort;
        this.nomers = nomers;
        this.logo = logo;
    }

    public Aircompany(AircompanyView aircompanyView)
    {
        this.id=aircompanyView.getId();
        this.nameLong=aircompanyView.getLongName();
        this.nameShort=aircompanyView.getShortName();
        //this.logo="/img/"+aircompanyView.
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameLong() {
        return nameLong;
    }

    public void setNameLong(String nameLong) {
        this.nameLong = nameLong;
    }

    public String getNameShort() {
        return nameShort;
    }

    public void setNameShort(String nameShort) {
        this.nameShort = nameShort;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }


    public Set<NomerReys> getNomers() {
        return nomers;
    }

    public void setNomers(Set<NomerReys> nomers) {
        this.nomers = nomers;
    }
}
