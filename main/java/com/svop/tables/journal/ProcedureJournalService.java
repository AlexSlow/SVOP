package com.svop.tables.journal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.sql.Date;

@Service
public abstract class ProcedureJournalService {
    @Autowired
    protected SeazonJournalRepository seazonJournalRepository;
    public void save(@NotNull String username,@NotNull Date date, TypeProcedure typeProcedure){
       seazonJournalRepository.save(new JournalProcedure(username,date,typeProcedure));
    }
    public  abstract JournalProcedure getLast();
}
