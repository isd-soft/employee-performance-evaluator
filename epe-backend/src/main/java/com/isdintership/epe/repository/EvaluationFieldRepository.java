package com.isdintership.epe.repository;

import com.isdintership.epe.entity.EvaluationField;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EvaluationFieldRepository extends JpaRepository<EvaluationField, String> {
    List<EvaluationField> findEvaluationFieldsByEvaluationGroupId(String id);

}
