package com.svop.service.handbooks;

import com.svop.View.NomerReysView;
import com.svop.View.NomerReysViewRequest;
import com.svop.tables.Handbooks.NomerReys;
import com.svop.tables.Handbooks.NomerReysRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NomerReysService {
    private static Logger logger= LoggerFactory.getLogger(NomerReysService.class.getName());
    @Autowired
    NomerReysRepository nomerReysRepository;
    @Autowired
    AircompaniesService aircompaniesService;
    public List<NomerReysView> getNomerReysFromAircompany(Integer id){
        logger.info("Запрос на получение номеров рейсов авиакомпании");
        List<NomerReys> nomerReys=nomerReysRepository.findByAircompany_Id(id);
        List<NomerReysView> nomerReysViews=new ArrayList<>();
        for(NomerReys item:nomerReys) nomerReysViews.add(new NomerReysView(item));
        return nomerReysViews;
    }
    public void delete(Iterable<Integer> id_list)
    {
        nomerReysRepository.deleteByIdIn(id_list);
    }
    public ResponseEntity<String> save(List<NomerReysView> nomerReysViews)
    {
       return new ResponseEntity<>("Успех",HttpStatus.OK);
    }
    public ResponseEntity<String> save(NomerReysViewRequest nomerReysViewRequest)
    {
        logger.info("Запрос на сохранение номеров рейсов авиакомпании");
        if (nomerReysViewRequest.getAicompany_id()==null)
        {
            logger.error("Авиакомпания не опеределена");
            return new ResponseEntity<String>("Авиакомпания не опеределена",HttpStatus.BAD_REQUEST);
        }
      //  List<NomerReys> nomerReys=new ArrayList<>();
        for(NomerReysView nomerReysView:nomerReysViewRequest.getNomers())
        {
            NomerReys nomer=new NomerReys();
            nomer.setId(nomerReysView.getId());
            nomer.setNomer(nomerReysView.getNomer());
            nomer.setAircompany(aircompaniesService.getByid(nomerReysViewRequest.getAicompany_id()).get());
            nomer.setType(nomerReysView.getType());
           // nomerReys.add(nomer);
            nomerReysRepository.save(nomer);
        }
        //nomerReysRepository.save(nomerReys);
       return new ResponseEntity<String>("Успех",HttpStatus.OK);
    }
}
