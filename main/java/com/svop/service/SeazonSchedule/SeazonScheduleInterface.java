package com.svop.service.SeazonSchedule;

import com.svop.View.SeazonScheduleViews.SeazonScheduleLanguageView;
import com.svop.View.SeazonScheduleViews.SeazonScheduleView;
import com.svop.other.HeadProcessing.PageFormatter;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SeazonScheduleInterface {
    public void forming();
    public List<SeazonScheduleView> getSeazonScheduleViews();
    public List<SeazonScheduleView> getSeazonScheduleViews(PageFormatter pageFormatter, Pageable pageable);
    public List<SeazonScheduleLanguageView> getSeazonScheduleLanguageViews(Pageable pageable, Integer country_nomer);
    public Integer getPageAmount(Pageable pageable);

}
