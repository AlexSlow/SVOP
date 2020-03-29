package com.svop.tables.Handbooks;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

public interface ReysyRepository extends JpaRepository<Reysy,Integer> {
    @Transactional
    void deleteByIdIn(List<Integer> list);
    List<Reysy> findAllByType(TypeReys type);
    //Iterable<Reysy> findAllBetweenPeriod_startAnAndPeriod_end(Calendar start,Calendar end);
}
