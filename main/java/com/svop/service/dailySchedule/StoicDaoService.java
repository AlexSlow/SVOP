package com.svop.service.dailySchedule;

import com.svop.View.DailyScheduleViews.FlightScheduleView;
import com.svop.View.DailyScheduleViews.StoicBindDto;
import com.svop.View.DailyScheduleViews.StoicDto;
import com.svop.View.DailyScheduleViews.StoicsAndFlightSheduleDto;
import com.svop.service.handbooks.RoutesService;
import com.svop.tables.daily_schedule.FlightSchedule;
import com.svop.tables.daily_schedule.Stoic;
import com.svop.tables.daily_schedule.StoicRepository;
import com.svop.tables.daily_schedule.StoicStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
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
        }
        return stoicDto;
    }
    @Override
    public List<StoicDto> getStoicsDtoList(@NotNull List<Stoic> stoics){
        List<StoicDto> stoicDtoList=new ArrayList<>(stoics.size());
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
    public List<StoicDto> getStoicsDtoListByIdList(@NotNull List<Integer> idList){
        List<Stoic> stoicList=stoicRepository.findByIdIn(idList);
        return getStoicsDtoList(stoicList);
    };
    @Override
    public  List<Stoic> stoicListFactory(List<StoicDto> stoicDtos)
    {
       // List<Integer> idl=new ArrayList<>(stoicDtos.size());
        List<Stoic> stoics=new ArrayList<>(stoicDtos.size());
        stoicDtos.forEach(dto->{
          //  idl.add(dto.getId()) ;
        });
        return null;

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
    @Override
    public void saveWithoutFlightSchedule(List<Stoic> stoics)
    {
        List<Stoic> saveList=new ArrayList<>();
        List<Integer> id=new ArrayList<>(stoics.size());
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

    @Override
    public List<StoicsAndFlightSheduleDto> getFlightSheduleByStoics(Integer stoicId) throws Exception {
      Optional<Stoic> stoicOptional= stoicRepository.findById(stoicId);
      if (stoicOptional.isPresent())
      {
       List<FlightScheduleView>  flightSchedules=flightSheduleDaoService.getActulaByDay(new Date(System.currentTimeMillis()));
       if (flightSchedules==null) return new ArrayList<>();
       if (flightSchedules.size()==0) return new ArrayList<>();
       List<StoicsAndFlightSheduleDto> flightSheduleDtos=new ArrayList<>(flightSchedules.size());
          FlightSchedule selected= stoicOptional.get().getFlightSchedule();
          int idSelect;
          if (selected==null) idSelect=-1;
          else idSelect=selected.getId();

       flightSchedules.forEach(flightScheduleView -> {
           //Получить график для тог что бы проверить

           List<Stoic> stoic=stoicRepository.findByFlightScheduleId(flightScheduleView.getId());
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
       });
       return flightSheduleDtos;
      }else
      {
          throw  new Exception("Не найдена стойка");
      }
    }

    @Override
    public void bind(@NotNull StoicBindDto stoicBindDto) throws Exception {
        Optional<Stoic> stoicOptional=stoicRepository.findById(stoicBindDto.getStoicId());
        if (stoicOptional.isPresent())
        {
            Optional<FlightSchedule> flightScheduleOptional=flightSheduleDaoService.getFlightSheduleById(stoicBindDto.getReysId());
            if (flightScheduleOptional.isPresent())
            {
                stoicOptional.get().setFlightSchedule(flightScheduleOptional.get());
                stoicRepository.save(stoicOptional.get());
                simpMessageSendingOperations.convertAndSend("/topic/stoic/"+stoicBindDto.getStoicId(),"ref");
            }else {
                throw  new Exception("Отстутствует рейс");
            }

            //Получение списка

        }else  throw  new Exception("Отстутствует стойка");
    }

    @Override
    public void fire(@NotNull List<Integer> idl) {
        List<Stoic> stoics=stoicRepository.findByIdIn(idl);
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
}
