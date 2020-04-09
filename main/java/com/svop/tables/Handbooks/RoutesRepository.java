package com.svop.tables.Handbooks;

import com.svop.tables.Users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RoutesRepository extends JpaRepository<Routes,Integer> {
    void deleteByIdIn(List<Integer> ids);
    Page<Routes> findAll(Pageable page);
}
