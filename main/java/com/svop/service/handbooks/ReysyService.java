package com.svop.service.handbooks;

import com.svop.View.ReysViewElement;
import com.svop.View.ReysyListView;
import com.svop.service.secutity.UserService;
import com.svop.tables.Handbooks.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Null;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class ReysyService {
    private static final Logger logger= LoggerFactory.getLogger(ReysyService.class.getName());
    @Autowired
    private RoutesService routesService;
    @Autowired
    private ReysyRepository reysyRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private NomerReysService nomerReysService;
    //Получить отображение для рейсов
    public List<ReysyListView> getReysListByType(TypeReys typeReys){
       logger.info("Получение списка рейсов");
       List<Reysy> reysyList= reysyRepository.findAllByType(typeReys);
       List<ReysyListView> reysyListViewList=new ArrayList<>();
       for (Reysy reys:reysyList)
       {
           ReysyListView reysyListView=new ReysyListView(reys,userService.getLocale(SecurityContextHolder.getContext().getAuthentication().getName()));
           reysyListView.setRout(routesService.getRouts(reys.getRout().getName()));
           reysyListViewList.add(reysyListView);
       }
    return reysyListViewList;
    }
    public ReysViewElement getReysById(Integer id){

        Optional<Reysy> reys=reysyRepository.findById(id);
        if(reys.isPresent()){return new ReysViewElement(reys.get());}else{return new ReysViewElement();}

    }
    //____________________________________________________________
    public void save(ReysViewElement reysViewElement){
        logger.info("Сохраниение рейса");
        if (reysViewElement==null){logger.error("Ошибка.Передан нулевой объект формы рейса"); return;}
        //Заполним рейс через его представление
        Reysy reysy=new Reysy();
        reysy.setId(reysViewElement.getId());
        //Получим номер рейса прилет
        Optional<NomerReys> nomer_prilet=nomerReysService.getByid(reysViewElement.getNomer_prilet_id());
        if (nomer_prilet.isPresent())
        {
            reysy.setNomer_prilet(nomer_prilet.get());

        }else
        {
            logger.error("Ошибка.Отсутствует номер рейса на прилет");
            return;
        }

        //Получим номер рейса вылет
        Optional<NomerReys> nomer_vilet=nomerReysService.getByid(reysViewElement.getNomer_vilet_id());
        if (nomer_prilet.isPresent())
        {
            reysy.setNomer_vilet(nomer_vilet.get());

        }else
        {
            logger.error("Ошибка.Отсутствует номер рейса на вылет");
            return;
        }

        //Получим маршрут
        Optional<Routes> rout=routesService.getById(reysViewElement.getRout());
        if (rout.isPresent())
        {
            reysy.setRout(rout.get());

        }else
        {
            logger.error("Ошибка.Отсутствует id маршрута!");
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.util.Date date=sdf.parse(reysViewElement.getPeriod_start());
           // System.out.println(date);
            reysy.setPeriod_start(new Date(sdf.parse(reysViewElement.getPeriod_start()).getTime()));
            reysy.setPeriod_end(new Date(sdf.parse(reysViewElement.getPeriod_start()).getTime()));
        } catch (ParseException e) {
            logger.error("Ошибка.Неверные форматы периодов!");
            e.printStackTrace();
            return;
        }

        //получим дни прилета и вылета
        List<Integer> prilet_days=reysViewElement.getPrilet_days();
        List<Integer> vilet_days=reysViewElement.getVilet_days();
        if ((prilet_days==null)||(vilet_days==null))
        {
            logger.error("Ошибка.Пустые списки дни прилета или вылета!");
            return;
        }
        //На прилет
        String prilet_d="";
        for(int i=0;i<reysViewElement.getPrilet_days().size();i++){
            //Для последнего элемента не нужно ставить слешь в конце
            if (i==reysViewElement.getPrilet_days().size()-1)
            {
                prilet_d=prilet_d+reysViewElement.getPrilet_days().get(i);
            }else{
                prilet_d=prilet_d+reysViewElement.getPrilet_days().get(i)+"/";
            }

        }
        reysy.setPrilet_days(prilet_d);
        //На вылет
        String vilet_d="";
        for(int i=0;i<reysViewElement.getVilet_days().size();i++){
            //Для последнего элемента не нужно ставить слешь в конце
            if (i==reysViewElement.getVilet_days().size()-1)
            {
                vilet_d=vilet_d+reysViewElement.getVilet_days().get(i);
            }else{
                vilet_d=vilet_d+reysViewElement.getVilet_days().get(i)+"/";
            }

        }
        reysy.setVilet_days(vilet_d);

        //Время отправления и прибытия для прилета и вылета
        reysy.setPrilet_time_otpravl(reysViewElement.getPrilet_time_otpravl());
        reysy.setPrilet_time_prib(reysViewElement.getPrilet_time_prib());
        reysy.setVilet_time_otpravl(reysViewElement.getVilet_time_otpravl());
        reysy.setVilet_time_prib(reysViewElement.getVilet_time_prib());


        //Остальные параметры
        reysy.setTip_vs(reysViewElement.getTip_vs());
        ReysyStatus reysyStatus=ReysyStatus.NOTChange;;
        if (reysViewElement.getIzmen_otmen()==null)
        {
            if (reysViewElement.getId()!=null) reysyStatus=ReysyStatus.Modified;
        }
        else if (reysViewElement.getIzmen_otmen().equals("on"))
        {reysyStatus=ReysyStatus.Canceled;}

        reysy.setIzmen_otmen(reysyStatus);
        reysy.setOsnovanie_izmen_otmen(reysViewElement.getOsnovanie_izmen_otmen());
        reysy.setType(reysViewElement.getType());
        reysy.setAirline(reysViewElement.getAirline());

        System.out.println(reysy);


        logger.info("Озавершение сохранения рейса");
        return;


    }
    public void delete(List<Integer> id_list){
        logger.info("Удаление списка рейсов");
        reysyRepository.deleteByIdIn(id_list);
    }
}
