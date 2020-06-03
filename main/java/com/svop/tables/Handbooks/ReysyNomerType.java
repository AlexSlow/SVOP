package com.svop.tables.Handbooks;

public enum ReysyNomerType {
     Прилет("nomer_reys_prilet"),Вылет("nomer_reys_vilet"),Транзит("nomer_reys_tranzit");
     private String name;
   ReysyNomerType(String name)
{
    this.name=name;
}
     ReysyNomerType(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
