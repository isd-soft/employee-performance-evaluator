package com.isdintership.epe.repository;

import com.isdintership.epe.entity.Assessment;
import com.isdintership.epe.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssessmentRepository extends JpaRepository<Assessment, String> {

    Optional<Assessment> findById(String id);

    List<Assessment> findByUser(User user);

    List<Assessment> findAllByIsTemplate(Boolean isTemplate);

    Optional<Assessment> findByTitleAndIsTemplate(String title, Boolean isTemplate);

    Optional<Assessment> findByTitleAndUser(String title, User user);

    Optional<Assessment> findByIdAndIsTemplate(String id, Boolean isTemplate);

    void removeById(String id);

}
