package com.svop.service.handbooks;

import com.svop.View.ReysViewElement;
import com.svop.View.ReysyListView;
import com.svop.service.secutity.UserService;
import com.svop.tables.Handbooks.*;
import com.svop.tables.temp.TempReysy;
import com.svop.tables.temp.TempReysyRepository;
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
import java.util.*;

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
    @Autowired
    private TempReysyRepository tempReysyRepository;
    @Autowired private SezonRepository sezonRepository;

    private List<ReysyListView> fillReysView(List<Reysy>reysyList)
    {
        List<ReysyListView> reysyListViewList = new ArrayList<>();
        for (Reysy reys : reysyList) {
            ReysyListView reysyListView = new ReysyListView(reys, userService.getLocale(SecurityContextHolder.getContext().getAuthentication().getName()));
            reysyListView.setRout(routesService.getRouts(reys.getRout().getName()));
            reysyListViewList.add(reysyListView);
        }
        return reysyListViewList;
    }
    //Получить отображение для рейсов
    public List<ReysyListView> getReysListByType(TypeReys typeReys,Integer sezon_selected){
       logger.info("Получение списка рейсов");
        List<Reysy> reysyList;
       if (sezon_selected!=null)
       {
           Optional<Sezon> sezon=sezonRepository.findById(sezon_selected);
           if (sezon.isPresent())
           {
               logger.info("Сезон найден");
              // System.out.println(sezon.get().getBegin()+" "+sezon.get().getEnd());
               reysyList=reysyRepository.findBetweenPeriodByType(typeReys,sezon.get().getBegin(),sezon.get().getEnd());
               return fillReysView(reysyList);
           }

       }
           logger.info("Отбор пропущен");
           reysyList = reysyRepository.findAllByType(typeReys);
            return fillReysView(reysyList);
    }
    public ReysViewElement getReysById(Integer id){

        Optional<Reysy> reys=reysyRepository.findById(id);
        if(reys.isPresent()){return new ReysViewElement(reys.get());}else{return new ReysViewElement();}

    }
    //____________________________________________________________
    /**
     * Сохранение в базу данных
     * @param reysViewElement Представление элемента рейса
     */

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
        //Тут пройдет проверка на удаленные дни
        refreshRemoveDays(reysy.getId(),prilet_days,vilet_days);

        //На прилет

        logger.info("Сформированы дни на прилет "+prilet_days);
        reysy.setPrilet_days(listToStringDays(prilet_days));//метод конвентирует списк в строку

        //На вылет

        logger.info("Сформированы дни на вылет "+vilet_days);
        reysy.setVilet_days(listToStringDays(vilet_days));//метод конвентирует списк в строку

        logger.info("Сформировано время отправления в Барнаул  "+reysViewElement.getPrilet_time_otpravl());
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

        //System.out.println(reysy);
        reysyRepository.save(reysy);


        logger.info("Озавершение сохранения рейса");
        return;


    }
    public void delete(List<Integer> id_list){
        logger.info("Удаление списка рейсов");
        reysyRepository.deleteByIdIn(id_list);
    }

    //----------------------------------------------------------------------------------------
    /**
     * Метод для работы с временной таблицей
     */
    private void refreshRemoveDays(Integer id_reys,List<Integer> prilet_days,List<Integer> vilet_days)
    {
        logger.info("Обновление удаленных рейсов  ");
        if (id_reys==null) return;
        //Так как рейс существует, то найдем дни, что были удалены
        Optional<Reysy> reysy=reysyRepository.findById(id_reys);
        if (!reysy.isPresent()) return; //Если рейса нет, то это новый рейс или где то ошибка!

        String[] prilet_days_in_past_array=reysy.get().getPrilet_days().split("/");
        String[] vilet_days_in_past_array=reysy.get().getVilet_days().split("/");

        //Дни, которые  были в рейсах до этого момента времени
        List<Integer> prilet_days_in_past=new ArrayList<>(7);
        List<Integer> vilet_days_in_past=new ArrayList<>(7);

        for (String item:prilet_days_in_past_array){
            if (!item.equals(""))
            prilet_days_in_past.add(Integer.parseInt(item));}
        for(String item:vilet_days_in_past_array){
            if (!item.equals(""))
            vilet_days_in_past.add(Integer.parseInt(item));}

        logger.info("В таблице содержатся такие значения на прилет "+prilet_days_in_past);
        logger.info("В таблице содержатся такие значения на вылет "+vilet_days_in_past);
        //Найдем, какие дни были Удалены между текущим сеансом
        List<Integer> prilet_days_has_daleted=new ArrayList<>(7);
        List<Integer> vilet_days_has_dalated=new ArrayList<>(7);
        for(Integer i:prilet_days_in_past)
        {
            //Если дня нет, тогда он удален
            boolean isFinded=false;
            for (Integer j:prilet_days)
            {
                if (i.equals(j)){isFinded=true;}
            }
            if (!isFinded){prilet_days_has_daleted.add(i);}
        }

        for(Integer i:vilet_days_in_past)
        {
            //Если дня нет, тогда он удален
            boolean isFinded=false;
            for (Integer j:vilet_days)
            {
                if (i.equals(j)){isFinded=true;}
            }
            if (!isFinded){vilet_days_has_dalated.add(i);}
        }

        logger.info("В текущий момент были отменены дни прилета  "+prilet_days_has_daleted);
        logger.info("В таблице содержатся такие значения на вылет "+vilet_days_has_dalated);

        //Мы нашли какие дни были отменены в текущий момент, теперь посмотрим, ессть ли уже запись
        Optional<TempReysy> tempReysy=tempReysyRepository.findByReysyId(id_reys);

        //Если записи не существует, тогда мы должны записать туда отмененные дни, если существует,то сначала убрать от туда
        //дни, которые были добавлены
        if (tempReysy.isPresent())
        {
            logger.info("Запись в временной таблице имеется ");
            //Если запись есть, тогда необходимо учесть старые отмеы дней
            String[] prilet_days_was_dalete_past_array=tempReysy.get().getTempPriletDays().split("/");
            String[] vilet_days_was_dalete_past_array=tempReysy.get().getTempViletDays().split("/");

            List<Integer> prilet_days_was_dalete_past=new ArrayList<>(7);
            List<Integer> vilet_days_was_dalete_past=new ArrayList<>(7);
            //Если строки пустые тогда

            for (String item:prilet_days_was_dalete_past_array){
                if (!item.equals(""))
                prilet_days_was_dalete_past.add(Integer.parseInt(item));
            }
            for(String item:vilet_days_was_dalete_past_array){
                if (!item.equals(""))
                vilet_days_was_dalete_past.add(Integer.parseInt(item));}

            logger.info("В таблице имеутся слудующие записи на прилет "+prilet_days_was_dalete_past);
            logger.info("В таблице имеутся слудующие записи на вылет "+vilet_days_was_dalete_past);

            //Теперь нам необходимо узнать, какие дни были добавлены, что бы урать их
            ListIterator<Integer> iterator_prilet=prilet_days_was_dalete_past.listIterator();
           while (iterator_prilet.hasNext())
           {
               Integer i=iterator_prilet.next();
               for (Integer j:prilet_days)
               {
                   //Если мы добавили удаленный день, тогда мы удаленный день удаляем
                   if (i.equals(j))
                   {
                       iterator_prilet.remove();
                       break;
                   }
               }
           }

            ListIterator<Integer> iterator_vilet=vilet_days_was_dalete_past.listIterator();
            while (iterator_vilet.hasNext())
            {
                Integer i=iterator_vilet.next();
                for (Integer j:vilet_days)
                {
                    //Если мы добавили удаленный день, тогда мы удаленный день удаляем
                    if (i.equals(j))
                    {
                        iterator_vilet.remove();
                        break;
                    }
                }
            }
            //Теперь сохраним полученные значения. Но сначала объеденим старые и новые коллекции
            List<Integer>  prilet_result_list=new ArrayList<>(7);
            List<Integer>  vilet_result_list=new ArrayList<>(7);
            prilet_result_list.addAll(prilet_days_has_daleted);
            prilet_result_list.addAll(prilet_days_was_dalete_past);

            vilet_result_list.addAll(vilet_days_has_dalated);
            vilet_result_list.addAll(vilet_days_was_dalete_past);

            logger.info("Итоговые значения для прилета "+prilet_result_list);
            logger.info("Итоговые значения для вылета "+vilet_result_list);

            //Сохранение
            tempReysy.get().setTempPriletDays(listToStringDays(prilet_result_list));
            tempReysy.get().setTempViletDays(listToStringDays(vilet_result_list));

        }else
        {
            logger.info("Запись в временной таблице отсутствует. Добавим просто отсутствующие дни ");
            //Создадним запись
            TempReysy tempReysyNew=new TempReysy();
            tempReysyNew.setReysyId(id_reys);
            tempReysyNew.setTempPriletDays(listToStringDays(prilet_days_has_daleted));
            tempReysyNew.setTempViletDays(listToStringDays(vilet_days_has_dalated));
            tempReysyRepository.save(tempReysyNew);
        }

    }
    /**
     * Форматттер для перевода из коллекции дней в строку
     */
    private String listToStringDays(List<Integer> days)
    {
        if (days==null) return "";
        String result="";
        for(int i=0;i<days.size();i++){
            //Для последнего элемента не нужно ставить слешь в конце
            if (i==days.size()-1)
            {
                result=result+days.get(i);
            }else{
                result=result+days.get(i)+"/";
            }

        }
        return result;
    }
}
