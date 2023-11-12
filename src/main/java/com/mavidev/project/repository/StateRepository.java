package com.mavidev.project.repository;

import com.mavidev.project.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {
    boolean existsByNameIgnoreCaseAndCity_Id(String name, Long id);
}