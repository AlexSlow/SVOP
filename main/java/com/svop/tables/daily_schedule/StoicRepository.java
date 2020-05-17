package com.svop.tables.daily_schedule;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoicRepository extends JpaRepository<Stoic,Integer> {
    void deleteByIdIn(List<Integer> id_list);
    List<Stoic> findByIdIn(List<Integer> idl);

    List<Stoic> findByFlightScheduleId(Integer id);
}
