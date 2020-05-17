package com.svop.tables.Handbooks;

import com.svop.View.AircompanyView;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.util.Objects;
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

    //Это таблица хозяин и у него нет внешнего ключа
    @OneToMany(mappedBy="aircompany")
    private Set<NomerReys> nomers;

    @Column(name="aircompany_logo")
    private String logo;

    @Column(name="aircompany_logo_lage")
    private String logoLage;

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

    public String getLogoLage() {
        return logoLage;
    }

    public void setLogoLage(String logoLage) {
        this.logoLage = logoLage;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Aircompany)) return false;
        Aircompany that = (Aircompany) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
