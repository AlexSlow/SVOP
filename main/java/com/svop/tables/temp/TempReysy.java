package com.svop.tables.temp;

import com.svop.tables.Handbooks.Reysy;

import javax.persistence.*;




@Entity
@Table(name = "temp_reysy")
public class TempReysy { @Id
@GeneratedValue(strategy= GenerationType.AUTO)
@Column(name="temp_reysy_id")
private Integer id;

//Каскадирование говорит, что что делать с владельцем при изменении другой таблицы. В данном случае мы сможем по отдельности
    //создать запись и рейс?
    //Cascade Merge отвечает, что сущьности объединяются при объединении
    //сущьности владельца

    //
    @Column(name="reysy_id")
    private Integer reysyId;

    @Column(name="temp_reysy_prilet_days")
    private String tempPriletDays;

    @Column(name="temp_reysy_vilet_days")
    private String tempViletDays;

    public TempReysy() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReysyId() {
        return reysyId;
    }

    public void setReysyId(Integer reysyId) {
        this.reysyId = reysyId;
    }

    public String getTempPriletDays() {
        return tempPriletDays;
    }

    public void setTempPriletDays(String tempPriletDays) {
        this.tempPriletDays = tempPriletDays;
    }

    public String getTempViletDays() {
        return tempViletDays;
    }

    public void setTempViletDays(String tempViletDays) {
        this.tempViletDays = tempViletDays;
    }
}
