package com.svop.service.dailySchedule;
import com.svop.View.DailyScheduleViews.*;
import com.svop.exeptions.SvopDataBaseExeption;
import com.svop.service.handbooks.RoutesService;
import com.svop.tables.daily_schedule.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.text.DateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@Transactional
public class StoicDaoService implements StoicDaoInterface {
    @Autowired private StoicRepository stoicRepository;
    @Autowired private FlightSheduleDaoService flightSheduleDaoService;
    @Autowired private RoutesService routesService;
    @Autowired
    private SimpMessageSendingOperations simpMessageSendingOperations; //Упаравление брокером сообщений
    private StoicDto stoicDtoFactory(@NotNull Stoic stoic){
        StoicDto stoicDto=new StoicDto();
        stoicDto.setId(stoic.getId());
        stoicDto.setNomer(stoic.getNomer());
        if ((stoic.getStatus()==null)||(stoic.getStatus().equals(StoicStatus.OFF))) stoicDto.setStatus(false);
        else if (stoic.getStatus().equals(StoicStatus.ON)) stoicDto.setStatus(true);
        if (stoic.getFlightSchedule()!=null){stoicDto.setNomerReys(stoic.getFlightSchedule().getDaily().getNomer().getNomer());}

        if (stoic.getFlightSchedule()!=null)
        {

            stoicDto.setRouteRu(routesService.getRouts(stoic.getFlightSchedule().getDaily().getRout().getName()));
            stoicDto.setRouteEn(routesService.getRoutsEn(stoic.getFlightSchedule().getDaily().getRout().getName()));
            stoicDto.setRouteCh(routesService.getRoutsCh(stoic.getFlightSchedule().getDaily().getRout().getName()));
            DateTimeFormatter formatterRu = DateTimeFormatter.ofPattern("HH:mm", new Locale("ru"));
            stoicDto.setTimeViletRu(formatterRu.format(stoic.getFlightSchedule().getTimeDeporture()));
            stoicDto.setImg(stoic.getFlightSchedule().getDaily().getNomer().getAircompany().getLogoLage());
            //Дата
           stoicDto.setDay(stoic.getFlightSchedule().getDay());
           stoicDto.setImgLittle(stoic.getFlightSchedule().getDaily().getNomer().getAircompany().getLogo());
        }
        return stoicDto;
    }
    @Override
    public List<StoicDto> getStoicsDtoList(@NotNull Iterable<Stoic> stoics){
        List<StoicDto> stoicDtoList=new ArrayList<>();
        stoics.forEach(stoic -> {stoicDtoList.add(stoicDtoFactory(stoic));});
        return stoicDtoList;

    };
    @Override
    public List<Stoic> findAll()
    {
        List<Stoic> stoicList=stoicRepository.findAll();
        return stoicList;
    }

    @Override
    public List<StoicDto> findDtoAll() {
       return getStoicsDtoList(stoicRepository.findAll());
    }

    @Override
    public List<StoicDto> getStoicsDtoListByIdList(@NotNull Iterable<Integer> idList){
        List<Stoic> stoicList=stoicRepository.findByIdInOrderByNomer(idList);
        return getStoicsDtoList(stoicList);
    };
    @Override
    public  List<Stoic> stoicListFactory(Iterable<StoicDto> stoicDtos)
    {
        List<Integer> idl=new ArrayList<>();
        stoicDtos.forEach(dto->{
            idl.add(dto.getId()) ;
        });
        List<Stoic> stoics=stoicRepository.findByIdInOrderByNomer(idl);
        return stoics;

    }
    private void findAndSet(Stoic stoic,List<Stoic> stoics,List<Stoic> save){
        stoics.forEach(stoic1 -> {
            if (stoic.equals(stoic1))
            {
                stoic1.setNomer(stoic.getNomer());
                save.add(stoic1);
            }
        });
    }

    /***
     * Сохранить коллекцию, в которой отсутствуют графики полетов
     * @param stoics
     */
    @Override
    public void saveWithoutFlightSchedule(Iterable<Stoic> stoics)
    {
        List<Stoic> saveList=new ArrayList<>();
        List<Integer> id=new ArrayList<>();
        stoics.forEach(stoic->{
            id.add(stoic.getId());
        });
        List<Stoic> list=stoicRepository.findAllById(id);
        stoics.forEach(stoic->{
            if (list.contains(stoic)) {
                findAndSet(stoic,list,saveList);
            }else
            {
                saveList.add(stoic);
            }
        });

        stoicRepository.saveAll(saveList);
    }

    /**
     * Вывести с учетом стойки
     * @param stoicId
     * @return
     * @throws Exception
     */
    @Override
    public List<StoicsAndFlightSheduleDto> getFlightSheduleByStoics(Integer stoicId) throws Exception {
      Optional<Stoic> stoicOptional= stoicRepository.findById(stoicId);
        List<FlightScheduleView>  flightSchedules=flightSheduleDaoService.getActulaViletReysByDay(new Date(System.currentTimeMillis()));
        if (flightSchedules==null) return new ArrayList<>();
        if (flightSchedules.size()==0) return new ArrayList<>();
        List<StoicsAndFlightSheduleDto> flightSheduleDtos=new ArrayList<>(flightSchedules.size());
        int idSelect=-1;
      if (stoicOptional.isPresent()) {
          FlightSchedule selected= stoicOptional.get().getFlightSchedule();
          if (selected==null) idSelect=-1;
          else idSelect=selected.getId();
      }

        for(FlightScheduleView flightScheduleView:flightSchedules)
      {
           //Получить график для тог что бы проверить
           List<Stoic> stoic=stoicRepository.findByFlightScheduleIdOrderByNomer(flightScheduleView.getId());
           String stoikaStr="Не задан";

           if (stoic!=null)
           {
               StringBuilder stringBuilder=new StringBuilder();
               stoic.forEach(stoic1 -> {
                   if (stringBuilder.length()==0)
                   {
                       stringBuilder.append(stoic1.getNomer()+" ");
                   }

               });
               stoikaStr=stringBuilder.toString();
           }
           if (flightScheduleView.getId().equals(idSelect))
           {
               flightSheduleDtos.add(new StoicsAndFlightSheduleDto(flightScheduleView,true,stoikaStr,stoicId));
           }else{
               flightSheduleDtos.add(new StoicsAndFlightSheduleDto(flightScheduleView,false,stoikaStr,stoicId));
           }
       }
       return flightSheduleDtos;

    }

    /**
     * Вывести рейс на стойку
     * @param stoicBindDto
     * @throws Exception
     */
    @Override
    public void bind(@NotNull StoicBindDto stoicBindDto) throws Exception {
        List<Stoic> stoicList=stoicRepository.findAllById(stoicBindDto.getStoicId());
        Optional<FlightSchedule> flightScheduleOptional = flightSheduleDaoService.getFlightSheduleById(stoicBindDto.getReysId());
        if ((stoicList!=null)||(!flightScheduleOptional.isPresent()))
        {
            for(Stoic stoic:stoicList) {
                    stoic.setFlightSchedule(flightScheduleOptional.get());
                    simpMessageSendingOperations.convertAndSend("/topic/stoic/" + stoic.getId(), "ref");
                }
            stoicRepository.saveAll(stoicList);
            }
        else  throw  new SvopDataBaseExeption("Отстутствует стойка или рейс");
    }
    /**
     * Включить выключить стоки
     * @param idl
     */
    @Override
    public void fire(@NotNull Iterable<Integer> idl) {
        List<Stoic> stoics=stoicRepository.findByIdInOrderByNomer(idl);
        stoics.forEach(stoic -> {
                if(stoic.getStatus()==null)
                {
                    stoic.setStatus(StoicStatus.ON);
                    simpMessageSendingOperations.convertAndSend("/topic/stoic/"+stoic.getId(),"ON");
                    System.out.println(stoic.getId());
                }
                else
                if(stoic.getStatus()==StoicStatus.OFF)
                {
                    stoic.setStatus(StoicStatus.ON);
                    simpMessageSendingOperations.convertAndSend("/topic/stoic/"+stoic.getId(),"ON");
                }
                else
                if(stoic.getStatus()==StoicStatus.ON)
                {
                    stoic.setStatus(StoicStatus.OFF);
                    simpMessageSendingOperations.convertAndSend("/topic/stoic/"+stoic.getId(),"OFF");

                }

        });
        stoicRepository.saveAll(stoics);


}
    @Override
    public StoicDto getStoic(@NotNull Integer id) {
        return stoicDtoFactory((stoicRepository.findById(id).get()));
    }

    @Override
    public void delete(Iterable<Integer> id) {
        stoicRepository.deleteByIdIn(id);
    }

    @Override
    @Cacheable(cacheNames = "informationStoics")
    public List<StoicLanguageDto> getInformationAboutStoics(Pageable pageable, Locale locale) {
        Page<Stoic> stoics=stoicRepository.findAllByFlightScheduleIsNotNull(pageable);
        List<StoicDto> stoicDtoList=this.getStoicsDtoList(stoics);
       // stoics.stream().filter(i->{return i.isStatus();});
        return stoicLanguageDtoListFactory(stoicDtoList,locale);
    }
    public List<StoicLanguageDto> stoicLanguageDtoListFactory(@NotNull Iterable<StoicDto> stoicDto,Locale locale){
        List<StoicLanguageDto> stoicLanguageDtos=new ArrayList<>();
        stoicDto.forEach(stoic->{
            stoicLanguageDtos.add(stoicLanguageDtoFactory(stoic,locale));
        });
        return stoicLanguageDtos;
    }
    /**
     * Для информационного табло
     * @param stoicDto
     * @param locale
     * @return
     */
    public StoicLanguageDto stoicLanguageDtoFactory(StoicDto stoicDto, Locale locale){
        StoicLanguageDto stoicLanguageDto=new StoicLanguageDto();
        stoicLanguageDto.setId(stoicDto.getId());
        stoicLanguageDto.setImg(stoicDto.getImgLittle());
        stoicLanguageDto.setNomer(stoicDto.getNomer());
        stoicLanguageDto.setNomerReys(stoicDto.getNomerReys());
        stoicLanguageDto.setStatus(stoicDto.isStatus());
        stoicLanguageDto.setDay(stoicDto.getDay());
        stoicLanguageDto.setTimeVilet(stoicDto.getTimeViletRu());

        switch (locale.getLanguage().toLowerCase())
        {
            case "ru": stoicLanguageDto.setRoute(stoicDto.getRouteRu());
            break;
            case "en": stoicLanguageDto.setRoute(stoicDto.getRouteEn());
            break;
            case "ch": stoicLanguageDto.setRoute(stoicDto.getRouteEn());
            break;
            default: stoicLanguageDto.setRoute(stoicDto.getRouteRu());
        }
        return stoicLanguageDto;
    }
}
