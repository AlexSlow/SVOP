package com.svop.tables.Handbooks;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AircompanyRepositpry extends JpaRepository<Aircompany,Long> {
    void deleteByIdIn(Iterable<Integer> ids);
    Page<Aircompany> findAll(Pageable pageable);
    Optional<Aircompany> findById(Integer aLong);
}
