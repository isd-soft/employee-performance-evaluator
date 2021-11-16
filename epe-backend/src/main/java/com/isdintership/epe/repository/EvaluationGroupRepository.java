package com.isdintership.epe.repository;

import com.isdintership.epe.entity.EvaluationGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluationGroupRepository extends JpaRepository<EvaluationGroup, String> {
    List<EvaluationGroup> findEvaluationGroupByAssessmentId (String id);
}
