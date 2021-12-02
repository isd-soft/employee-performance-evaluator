package com.isdintership.epe.repository;

import com.isdintership.epe.entity.AssessmentInformation;
import com.isdintership.epe.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AssessmentInformationRepository extends JpaRepository<AssessmentInformation, String> {

    List<AssessmentInformation> findByPerformedByUser(User user);
    List<AssessmentInformation> findByPerformedOnUser(User user);
}
