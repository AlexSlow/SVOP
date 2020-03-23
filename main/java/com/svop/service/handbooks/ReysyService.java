package com.svop.service.handbooks;

import com.svop.tables.Handbooks.Reysy;
import com.svop.tables.Handbooks.ReysyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReysyService {
    @Autowired
    private ReysyRepository reysyRepository;
    public List<Reysy> get(){
       return reysyRepository.findAll();
    }
    public void delete(){}
}
