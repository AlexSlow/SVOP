package com.svop.View;

import java.util.ArrayList;

/**
 * Для сохранения за авиакомпанией номнеров рейсов
 */
public class NomerReysViewRequest {
    private ArrayList<NomerReysView> nomers;
    private Integer aicompany_id;
    public NomerReysViewRequest() {
    }

    public NomerReysViewRequest(ArrayList<NomerReysView> nomers, Integer aicompany_id) {
        this.nomers = nomers;
        this.aicompany_id = aicompany_id;
    }

    public ArrayList<NomerReysView> getNomers() {
        return nomers;
    }

    public void setNomers(ArrayList<NomerReysView> nomers) {
        this.nomers = nomers;
    }

    public Integer getAicompany_id() {
        return aicompany_id;
    }

    public void setAicompany_id(Integer aicompany_id) {
        this.aicompany_id = aicompany_id;
    }

    @Override
    public String toString() {
        return "NomerReysViewRequest{" +
                "nomers=" + nomers +
                ", aicompany_id=" + aicompany_id +
                '}';
    }
}
