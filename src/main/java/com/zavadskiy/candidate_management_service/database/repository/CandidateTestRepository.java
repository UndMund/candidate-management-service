package com.zavadskiy.candidate_management_service.database.repository;

import com.zavadskiy.candidate_management_service.database.entity.CandidateTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateTestRepository extends JpaRepository<CandidateTest, Long>, QuerydslPredicateExecutor<CandidateTest> {

}
