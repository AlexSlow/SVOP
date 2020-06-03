package com.svop.tables.journal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.sql.Date;

@Service
public class SeazonProcedureJournalService {
    @Autowired
    private SeazonJournalRepository seazonJournalRepository;
    public void save(@NotNull String username,@NotNull Date date){
       seazonJournalRepository.save(new SeazonJournalProcedure(username,date));
    }
    public SeazonJournalProcedure getLast(){
        return seazonJournalRepository.findFirstByOrderByDateDesc();
    }
}
