package com.svop.service.handbooks;


import com.svop.exeptions.SvopDataBaseExeption;
import com.svop.tables.Handbooks.Airporty;
import com.svop.tables.Handbooks.AirportyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional(rollbackFor = SvopDataBaseExeption.class)
@Service
public class AirportsService  {
    @Autowired
    AirportyRepo airportyRepo;

    public void save(Airporty aiporty)   {
        try
        {
            airportyRepo.save(aiporty);
        }catch (RuntimeException ex)
        {
            ex.getLocalizedMessage();
            System.out.println(ex.getLocalizedMessage());
        }

    }

    public void save(List<?> notes) {
            for (Object note : notes) {
                Airporty aiporty = (Airporty) note;
                save(aiporty);
            }
    }

    public void deleteById(List<Integer> id_list) {
        if (id_list!=null)
                airportyRepo.deleteByIdIn(id_list);
    }
    public Page<Airporty> getPage(Pageable pageable) {
        return airportyRepo.findAll(pageable);
    }
}
