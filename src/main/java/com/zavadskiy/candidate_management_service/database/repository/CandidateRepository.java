package com.zavadskiy.candidate_management_service.database.repository;

import com.zavadskiy.candidate_management_service.database.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long>, QuerydslPredicateExecutor<Candidate> {

}
