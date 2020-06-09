package com.svop.View.DailyScheduleViews;

import lombok.Data;

import java.util.List;
@Data
public class StoicBindDto {
    private Integer reysId;
    private List<Integer> stoicId;

    public StoicBindDto() {
    }

}
