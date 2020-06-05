package com.svop.tables.journal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeazonJournalRepository extends JpaRepository<JournalProcedure,Integer> {
    Page<JournalProcedure> findAllBy(Pageable pageable);
    JournalProcedure findFirstByTypeProcedureOrderByDateDesc(TypeProcedure typeProcedure);
}
