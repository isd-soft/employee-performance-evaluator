package com.isdintership.epe.util;

import com.isdintership.epe.dto.AssessmentDto;
import com.isdintership.epe.entity.Assessment;

public final class AssessmentUtil {
    public static AssessmentDto toAssessmentDto(Assessment assessment) {
        return new AssessmentDto(
            assessment.getId(),
            assessment.getTitle(),
            assessment.getDescription(),
            assessment.getJob().getJobTitle(),
            assessment.getOverallScore(),
            assessment.getStatus(),
            assessment.getIsTemplate(),
            assessment.getStartDate(),
            assessment.getEndDate(),
            assessment.getUser().getId(),
            assessment.getEvaluationGroups()
        );
    }
}
