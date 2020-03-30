package com.svop.tables.Handbooks;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SezonRepository extends JpaRepository<Sezon,Integer> {
    @Transactional
    public void deleteByIdIn(List<Integer> id);
}
