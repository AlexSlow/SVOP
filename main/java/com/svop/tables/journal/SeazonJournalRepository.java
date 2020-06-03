package com.svop.tables.journal;

import com.svop.tables.Handbooks.Sezon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeazonJournalRepository extends JpaRepository<SeazonJournalProcedure,Integer> {
    Page<SeazonJournalProcedure> findAllBy(Pageable pageable);
    SeazonJournalProcedure findFirstByOrderByDateDesc();
}
