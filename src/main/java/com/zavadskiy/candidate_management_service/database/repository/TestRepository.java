package com.zavadskiy.candidate_management_service.database.repository;

import com.zavadskiy.candidate_management_service.database.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TestRepository extends JpaRepository<Test, Long>, QuerydslPredicateExecutor<Test> {
    Optional<Test> findByName(String name);
}
