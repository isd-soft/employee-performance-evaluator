package com.isdintership.epe.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.isdintership.epe.entity.AssessmentInformation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AssessmentInformationDto {

    private String assessmentTitle;
    private String performedAction;
    private String performedOnUser;
    private String performedByUser;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime performedTime;


    public AssessmentInformationDto(AssessmentInformation assessmentInformation){
        this.assessmentTitle = assessmentInformation.getAssessmentTitle();
        this.performedByUser = assessmentInformation.getPerformedByUser().getFirstname() + " " +assessmentInformation.getPerformedByUser().getLastname();
        this.performedOnUser = assessmentInformation.getPerformedOnUser().getFirstname() + " " + assessmentInformation.getPerformedOnUser().getLastname();
        this.performedAction = assessmentInformation.getPerformedAction();
        this.performedTime = assessmentInformation.getPerformedTime();

    }
}
