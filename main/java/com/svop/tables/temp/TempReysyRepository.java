package com.svop.tables.temp;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TempReysyRepository extends JpaRepository<TempReysy,Integer> {

    Optional<TempReysy> findByReysyId(Integer id);

}
