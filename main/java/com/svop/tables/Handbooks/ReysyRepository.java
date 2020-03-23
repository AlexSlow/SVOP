package com.svop.tables.Handbooks;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;

public interface ReysyRepository extends JpaRepository<Reysy,Long> {
    @Transactional
    void deleteByIdIn(Iterable<Integer> iterable);
    //Iterable<Reysy> findAllBetweenPeriod_startAnAndPeriod_end(Calendar start,Calendar end);
}
