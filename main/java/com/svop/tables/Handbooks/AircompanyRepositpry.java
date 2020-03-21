package com.svop.tables.Handbooks;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AircompanyRepositpry extends JpaRepository<Aircompany,Long> {
    @Transactional
    void deleteByIdIn(Iterable<Integer> ids);

    Optional<Aircompany> findById(Integer aLong);
}
