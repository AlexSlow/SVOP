package com.svop.tables.journal;

import org.springframework.stereotype.Service;

@Service("Daily")
public class ProcedureRegistratorDaily extends ProcedureJournalService {
    @Override
    public JournalProcedure getLast() {
        return seazonJournalRepository.findFirstByTypeProcedureOrderByDateDesc(TypeProcedure.Ежедневное);
    }
}
