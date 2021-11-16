package com.isdintership.epe.util;

import com.isdintership.epe.dto.AssessmentDto;
import com.isdintership.epe.entity.Assessment;
import com.isdintership.epe.entity.Job;
import com.isdintership.epe.entity.StatusEnum;
import com.isdintership.epe.entity.User;
import com.isdintership.epe.exception.JobNotFoundException;
import com.isdintership.epe.exception.UserNotFoundException;
import com.isdintership.epe.repository.JobRepository;
import com.isdintership.epe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

public final class AssessmentUtil {

    public static AssessmentDto toAssessmentDto(Assessment assessment){
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
            assessment.getUser().getId());
    }

}
