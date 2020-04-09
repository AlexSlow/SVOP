package com.svop.service.handbooks;

import com.svop.View.RoutesView;
import com.svop.other.HeadProcessing.PageFormatter;
import com.svop.tables.Handbooks.Airporty;
import com.svop.tables.Handbooks.AirportyRepo;
import com.svop.tables.Handbooks.Routes;
import com.svop.tables.Handbooks.RoutesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Transactional
@Service
public class RoutesService {
    public static Logger logger= LoggerFactory.getLogger(RoutesService.class.getName());
    @Autowired
   private RoutesRepository routesRepository;
    @Autowired
   private AirportyRepo airportyRepo;

    /**
     *
     * @param airports_sequence Это строка, содержащая id аэропортов
     * @param id Отвечает за возможность сохраненеия
     * @return Возвращает В случае успеха строку о успехе
     */
    //Сохранить последовательность
    public ResponseEntity<String> save(String airports_sequence, Integer id)
    {
        List<Airporty> airporty = airportyRepo.findByNameRu("Барнаул");

        if (airporty.size()!=1)
        {
            logger.error("Ошибка. Не найден целевой аэропорт в Базе данных");
            return new ResponseEntity<String>("Не найден целевой аэропорт в БД", HttpStatus.BAD_REQUEST);
        }
        Airporty airport=airporty.get(0);//get Barnaul
        String[] airports_id=airports_sequence.split("/");

        if (airports_id.length<2)
        {
            logger.error("Ошибка.Мало параметров в маршруте");
            return new ResponseEntity<String>("Мало параметров для построения маршрута",
                    HttpStatus.BAD_REQUEST);
        }
        boolean isBax=false;
        for(String item:airports_id)
    {
if (Integer.parseInt(item)==airport.getId()) {isBax=true;break;}
    }
        if (!isBax)
            {
                logger.error("Ошибка. Не найден целевой аэропорт или присудствет его дубликат!");
                return new ResponseEntity<String>("Не найден целевой аэропорт", HttpStatus.BAD_REQUEST);
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
        if (bax==null){
            logger.error("Не найден целевой аэропорт!");
                return  new ResponseEntity<String>("Не найден целевой аэропорт!",
                HttpStatus.BAD_REQUEST);
        }
        if((arrival!=null)&&(route_vilet!=null)) {route_prilet=route_vilet=airports_sequence;}
        else if(arrival!=null){route_vilet=arrival+"/"+airports_id[bax]; route_prilet=airports_id[bax]+"/"+arrival;}
        else if(deporture!=null){route_prilet=deporture+"/"+airports_id[bax]; route_vilet=airports_id[bax]+"/"+deporture;}


        Routes routes= new Routes(airports_sequence,
                airportyRepo.findById(arrival),
                airportyRepo.findById(deporture),route_prilet,route_vilet);
        if (id!=null) routes.setId(id);
        routesRepository.save(routes);
        logger.info("Формирование маршрута прошло успешно");
        return new ResponseEntity<String>("Маршрут успешно сформирован",
                HttpStatus.OK);

    }

    /**
     * Получить по id
     * @param id
     * @return
     */
    public Optional<Routes> getById(Integer id)
    {
        if (id==null)
        {
            logger.error("Ошибка. Передана пустой id  маршрута!");
            return Optional.empty();
        }
        return routesRepository.findById(id);

    }

    /**
     * Сохранить список
     * @param routesViewArrayList
     * @return
     */
    public ResponseEntity<String> save(ArrayList<RoutesView> routesViewArrayList){
        if (routesViewArrayList==null) {
            logger.error("Ошибка. Передана пустаня ссылка маршрутов!");
            return  new ResponseEntity<String>("Пустой список!",
                    HttpStatus.BAD_REQUEST);};
        for (RoutesView item:routesViewArrayList) {
            //System.out.println(item);
            this.save(item.getName(), item.getId());
        }
        return new ResponseEntity<>("Сохранение успешно завершено",HttpStatus.OK);
    }


    /**
     * Удалить Список
     * @param idl
     */

    public void delete(List<Integer>idl)
    {
        routesRepository.deleteByIdIn(idl);
    }

    /**
     * ПОлучить списк для вывода на форму
     * @return
     */
//Ответ для представления маршрутов
    public ArrayList<RoutesView> getRouts(){
        List<Routes> routs=routesRepository.findAll();
        ArrayList<RoutesView> output_routes=new ArrayList<>();

        for(Routes rout:routs)
        {

            RoutesView routesView=new RoutesView();
            routesView.setId(rout.getId());
            String[] airports_id_into_route=rout.getName().split("/");
            for(String id:airports_id_into_route)
            {
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

    //Ответ для представления маршрутов

    /**
     * Получение страницы маршрутов
     * @param pageable
     * @return
     */
    public List<RoutesView> getPageRouts(PageFormatter pageFormatter, Pageable pageable){
        Page<Routes> routs=routesRepository.findAll(pageable);
        pageFormatter.setSize(routs.getTotalPages()); //Для контроллера
        ArrayList<RoutesView> output_routes=new ArrayList<>();

        for(Routes rout:routs)
        {

            RoutesView routesView=new RoutesView();
            routesView.setId(rout.getId());
            String[] airports_id_into_route=rout.getName().split("/");
            for(String id:airports_id_into_route)
            {
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
        logger.info("Формирование ответа траниц маршрутов для представления маршрутов");
        return  output_routes;
    }


    /**
     * На вход подается строка, на выход маршрут на основном языке
     * @param route_string
     * @return
     */
    //Вернуть маршрут из строки
   public String getRouts(String route_string)
   {
       String[] airports_id=route_string.split("/");
       String response="";
       for (String id:airports_id)
       {
           Integer id_int=Integer.valueOf(id);
           Airporty airporty=airportyRepo.findById(id_int);
           if (response.length()!=0){response=response+"—"+airporty.getNameRu();}else response=airporty.getNameRu();

       }
       return response;
   }

    /**
     * На вход подается строка, на выход маршрут на английском языке
     * @param route_string
     * @return
     */
    //Вернуть маршрут из строки
    public String getRoutsEn(String route_string)
    {
        String[] airports_id=route_string.split("/");
        String response="";
        for (String id:airports_id)
        {
            Integer id_int=Integer.valueOf(id);
            Airporty airporty=airportyRepo.findById(id_int);
            if (response.length()!=0){response=response+"—"+airporty.getNameEng();}else response=airporty.getNameEng();

        }
        return response;
    }
    /**
     * На вход подается строка, на выход маршрут на китайском языке
     * @param route_string
     * @return
     */
    //Вернуть маршрут из строки
    public String getRoutsCh(String route_string)
    {
        String[] airports_id=route_string.split("/");
        String response="";
        for (String id:airports_id)
        {
            Integer id_int=Integer.valueOf(id);
            Airporty airporty=airportyRepo.findById(id_int);
            if (response.length()!=0){response=response+"—"+airporty.getNameCh();}else response=airporty.getNameCh();

        }
        return response;
    }
    /**
     * На вход подается число -номер страны и строка с маршрутами , на выход маршрут на на нужном языке языке
     * @param route_string
     * @return
     */
    public String getRoutsByNomer(Integer nomer_country,String route_string)
    {
        switch (nomer_country) {
            case 0:
                return getRouts(route_string);

            case 1:
                return getRoutsEn(route_string);

            case 2:
                return getRoutsCh(route_string);
            default:
                return getRouts(route_string);
        }

    }
}
