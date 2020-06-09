package com.svop.tables.daily_schedule;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoicRepository extends JpaRepository<Stoic,Integer> {
    void deleteByIdIn(Iterable<Integer> id_list);
    List<Stoic> findByIdInOrderByNomer(Iterable<Integer> idl);

    List<Stoic> findByFlightScheduleIdOrderByNomer(Integer id);
    Page<Stoic> findAllByFlightScheduleIsNotNull(Pageable pageable);
}
