package com.zavadskiy.candidate_management_service.database.repository;

import com.zavadskiy.candidate_management_service.database.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FileRepository extends JpaRepository<File, String> {
}
