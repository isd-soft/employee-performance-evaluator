package com.isdintership.epe.repository;

import com.isdintership.epe.dto.AssessmentDto;
import com.isdintership.epe.entity.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssessmentRepository extends JpaRepository<Assessment, String> {

    Optional<Assessment> findById(String id);

    List<Assessment> findByUserId(String id);

    Optional<Assessment> removeById(String id);
}
