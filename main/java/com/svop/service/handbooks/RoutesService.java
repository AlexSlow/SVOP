package com.svop.service.handbooks;

import com.svop.View.RoutesView;
import com.svop.tables.Handbooks.Airporty;
import com.svop.tables.Handbooks.AirportyRepo;
import com.svop.tables.Handbooks.Routes;
import com.svop.tables.Handbooks.RoutesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoutesService {
    public static Logger logger= LoggerFactory.getLogger(RoutesService.class.getName());
    @Autowired
    RoutesRepository routesRepository;
    @Autowired
    AirportyRepo airportyRepo;

    /**
     *
     * @param airports_sequence Это строка, содержащая id аэропортов
     * @param id Отвечает за возможность сохраненеия
     * @return Возвращает В случае успеха строку о успехе
     */
    public String save(String airports_sequence,Integer id)
    {
        List<Airporty> airporty = airportyRepo.findByNameRu("Барнаул");

        if (airporty.size()!=1)
        {
            logger.error("Ошибка. Не найден целевой аэропорт или присудствет его дубликат!");
            System.out.println("Ошибка");
            return null;
        }
        Airporty airport=airporty.get(0);//get Barnaul
        String[] airports_id=airports_sequence.split("/");
        if (airports_id.length<2)
        {
            logger.error("Ошибка.Мало параметров в маршруте");
            return null;
        }
       // System.out.println(item);
        Integer arrival=null;
        Integer deporture=null;
        String route_prilet="";
        String route_vilet="";
        Integer bax=null;
        for(int i=0;i<airports_id.length;i++)
        {

            if(airport.getId()==i)
            {
                bax=i;
                if (i==airports_id.length-1)
                {
                    deporture=Integer.valueOf(airports_id[i-1]);
                    break;
                }else if (i==0)
                {
                    arrival=Integer.valueOf(airports_id[i+1]);
                    break;
                }else {
                    deporture=Integer.valueOf(airports_id[i-1]);
                    arrival=Integer.valueOf(airports_id[i+1]);
                    break;
                }
            }
        }
        if (bax==null){logger.error("Не найден целевой аэропорт!"); return "error";}
        if((arrival!=null)&&(route_vilet!=null)) {route_prilet=route_vilet=airports_sequence;}
        else if(arrival!=null){route_vilet=arrival+"/"+airports_id[bax]; route_prilet=airports_id[bax]+"/"+arrival;}
        else if(deporture!=null){route_prilet=deporture+"/"+airports_id[bax]; route_vilet=airports_id[bax]+"/"+deporture;}


        Routes routes= new Routes(airports_sequence,
                airportyRepo.findById(arrival),
                airportyRepo.findById(deporture),route_prilet,route_vilet);
        if (id!=null) routes.setId(id);
        routesRepository.save(routes);
        logger.info("Формирование маршрута прошло успешно");
        return "success";

    }
    public String save(ArrayList<RoutesView> routesViewArrayList){
        if (routesViewArrayList==null) {
            logger.error("Ошибка. Передана пустаня ссылка маршрутов!");
            return null;};
        for (RoutesView item:routesViewArrayList) {
            //System.out.println(item);
            this.save(item.getName(), item.getId());
        }
        return "Success";
    }




    public void delete(List<Integer>idl)
    {
        routesRepository.deleteByIdIn(idl);
    }
    public ArrayList<RoutesView> getRouts(){
        List<Routes> routs=routesRepository.findAll();
        ArrayList<RoutesView> output_routes=new ArrayList<>();

        for(Routes rout:routs)
        {

            RoutesView routesView=new RoutesView();
            routesView.setId(rout.getId());
            String[] airports_id_into_route=rout.getName().split("/");
            //String route_str_name="";
            for(String id:airports_id_into_route)
            {
               // System.out.println(airportyRepo);
               Airporty airporty= airportyRepo.findById(Integer.parseInt(id));

               if (!routesView.getName().isEmpty())
               {
                   routesView.setName( routesView.getName()+"—"+airporty.getNameRu());
               }else
               {
                   routesView.setName(airporty.getNameRu());
               }

            }
            //Получить результат
            output_routes.add(routesView);
        }
        logger.info("Формирование ответа для представления маршрутов");
        return  output_routes;
    }
}
