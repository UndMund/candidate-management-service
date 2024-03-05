package com.zavadskiy.candidate_management_service.database.repository;

import com.zavadskiy.candidate_management_service.database.entity.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpecialityRepository extends JpaRepository<Speciality, Long>, QuerydslPredicateExecutor<Speciality> {
    Optional<Speciality> findByName(String name);
}
