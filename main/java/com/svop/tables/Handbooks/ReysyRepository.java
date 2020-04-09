package com.svop.tables.Handbooks;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

public interface ReysyRepository extends JpaRepository<Reysy,Integer> {
    void deleteByIdIn(List<Integer> list);
    List<Reysy> findAllByType(TypeReys type);
    Page<Reysy> findAllByType(TypeReys type,Pageable pageable);
    @Query("select reys from Reysy reys where reys.type =:typeReys AND" +
            " (reys.period_start >= :date_start AND reys.period_start < :date_end)" +
            "AND " +
            "(reys.period_end > :date_start AND reys.period_end <= :date_end)")
    List<Reysy> findBetweenPeriodByType(
            @Param("typeReys") TypeReys typeReys,
            @Param("date_start") Date date_start,
            @Param("date_end") Date date_end);


    @Query("select reys from Reysy reys where reys.type =:typeReys AND" +
            " (reys.period_start >= :date_start AND reys.period_start < :date_end)" +
            "AND " +
            "(reys.period_end > :date_start AND reys.period_end <= :date_end)")
Page<Reysy> findBetweenPeriodByType(
        @Param("typeReys") TypeReys typeReys,
        @Param("date_start") Date date_start,
        @Param("date_end") Date date_end,Pageable pageable);

    @Query("select reys from Reysy reys where reys.type =:typeReys AND reys.period_end > :date")
    List<Reysy> findActualReysByDate(
            @Param("typeReys") TypeReys typeReys,
            @Param("date") Date date);

}
