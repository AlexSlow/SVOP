package com.svop.service.SeazonSchedule;

import com.svop.View.SeazonScheduleViews.SeazonScheduleView;
import com.svop.other.HeadProcessing.PageFormatter;
import com.svop.service.handbooks.ReysyService;
import com.svop.service.handbooks.RoutesService;
import com.svop.service.secutity.UserService;
import com.svop.tables.Handbooks.Reysy;
import com.svop.tables.Handbooks.ReysyStatus;
import com.svop.tables.Handbooks.TypeReys;
import com.svop.tables.SeazonSchedule.SeazonSchedule;
import com.svop.tables.SeazonSchedule.SeazonScheduleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class SeazonScheduleService {
    private static  final Logger logger= LoggerFactory.getLogger(SeazonSchedule.class);
    @Autowired
    private ReysyService reysyService;
    @Autowired private RoutesService routesService;
    @Autowired private SeazonScheduleRepository sezonScheduleRepository;
    @Autowired private UserService userService;
    @Transactional
    public void forming()
    {
        logger.info("Начало формирования сезонного расписания");
        sezonScheduleRepository.deleteAll();
        //Сформируем сезонное расписание
        List<Reysy> reysyList=reysyService.getActualReysListByType(TypeReys.Regular,new Date(System.currentTimeMillis()));

        for (Reysy reysy:reysyList)
        {
           // System.out.println(reysy);
            if (reysy.getIzmen_otmen().equals(ReysyStatus.Canceled)) continue;

            SeazonSchedule seazonSchedule=new SeazonSchedule();
            seazonSchedule.setId(reysy.getId());
            //Получим маршруты
            seazonSchedule.setRoutRu(routesService.getRouts(reysy.getRout().getName()));
            seazonSchedule.setRoutEn(routesService.getRoutsEn(reysy.getRout().getName()));
            seazonSchedule.setRoutCh(routesService.getRoutsCh(reysy.getRout().getName()));
            //Номера рейсов
            seazonSchedule.setNomerPrilet(reysy.getNomer_prilet().getNomer());
            seazonSchedule.setNomerVilet(reysy.getNomer_vilet().getNomer());
            //Периоды
            seazonSchedule.setPeriodStart(reysy.getPeriod_start());
            seazonSchedule.setPeriodEnd(reysy.getPeriod_end());
            //Дни
            seazonSchedule.setPriletDays(reysy.getPrilet_days());
            seazonSchedule.setViletDays(reysy.getVilet_days());
            //Времена
            seazonSchedule.setPrilet_time_otpravl(reysy.getPrilet_time_otpravl());
            seazonSchedule.setPrilet_time_prib(reysy.getPrilet_time_prib());
            seazonSchedule.setVilet_time_otpravl(reysy.getVilet_time_otpravl());
            seazonSchedule.setVilet_time_prib(reysy.getVilet_time_prib());
            //Остальное
            seazonSchedule.setTip_vs(reysy.getTip_vs());
            seazonSchedule.setAirline(reysy.getAirline());

            seazonSchedule.setAircompany(reysy.getNomer_prilet().getAircompany().getNameLong());
            seazonSchedule.setImg(reysy.getNomer_prilet().getAircompany().getLogo());
            sezonScheduleRepository.save(seazonSchedule);
        }
    }

    public List<SeazonScheduleView> getSeazonScheduleViews()
    {
        List<SeazonSchedule> seazonSchedules=sezonScheduleRepository.findAll();
        List<SeazonScheduleView> seazonScheduleViews=new ArrayList<>(seazonSchedules.size());
        String locale=userService.getLocale(SecurityContextHolder.getContext().getAuthentication().getName());
        for(SeazonSchedule item:seazonSchedules)
        {
            seazonScheduleViews.add(new SeazonScheduleView(item,locale));
        }
        return seazonScheduleViews;
    }

    public List<SeazonScheduleView> getSeazonScheduleViews(PageFormatter pageFormatter, Pageable pageable)
    {
        Page<SeazonSchedule> seazonSchedules=sezonScheduleRepository.findAll(pageable);
        pageFormatter.setSize(seazonSchedules.getTotalPages());
        List<SeazonScheduleView> seazonScheduleViews=new ArrayList<>(seazonSchedules.getTotalPages());
        String locale=userService.getLocale(SecurityContextHolder.getContext().getAuthentication().getName());
        for(SeazonSchedule item:seazonSchedules)
        {
            seazonScheduleViews.add(new SeazonScheduleView(item,locale));
        }
        return seazonScheduleViews;
    }
}
