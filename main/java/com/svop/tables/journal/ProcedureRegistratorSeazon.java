package com.svop.tables.journal;

import org.springframework.stereotype.Service;

@Service("Seazon")
public class ProcedureRegistratorSeazon extends ProcedureJournalService {
    @Override
    public JournalProcedure getLast() {
        return seazonJournalRepository.findFirstByTypeProcedureOrderByDateDesc(TypeProcedure.Сезонное);
    }
}
