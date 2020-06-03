package com.svop.View;

import com.svop.tables.Handbooks.NomerReys;
import com.svop.tables.Handbooks.ReysyNomerType;

import java.util.Locale;

public class NomerReysView {
    private String nomer;
    private ReysyNomerType type;
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public NomerReysView(String nomer, ReysyNomerType type, Integer id) {
        this.nomer = nomer;
        this.type = type;
        this.id = id;
    }

    public NomerReysView(){}

      public  NomerReysView(NomerReys nomerReys)
      {
          this.nomer = nomerReys.getNomer();
          this.type =nomerReys.getType() ;
          this.id=nomerReys.getId();
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

    @Override
    public String toString() {
        return "NomerReysView{" +
                "nomer='" + nomer + '\'' +
                ", type=" + type +
                ", id=" + id +
                '}';
    }
}
