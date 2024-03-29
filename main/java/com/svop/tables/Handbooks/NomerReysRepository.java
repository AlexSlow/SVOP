package com.svop.tables.Handbooks;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface NomerReysRepository extends JpaRepository<NomerReys,Integer> {
    List<NomerReys> findByAircompany_Id(Integer id);
    void deleteByIdIn(Iterable<Integer> id);
    void save(Iterable<NomerReys> nomerReys);



}
