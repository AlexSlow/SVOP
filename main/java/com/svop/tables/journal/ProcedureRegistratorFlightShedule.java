package com.svop.tables.journal;

import org.springframework.stereotype.Service;

@Service("FlightSchedule")
public class ProcedureRegistratorFlightShedule extends ProcedureJournalService {
    @Override
    public JournalProcedure getLast() {
        return seazonJournalRepository.findFirstByTypeProcedureOrderByDateDesc(TypeProcedure.Ежедневное);
    }
}
