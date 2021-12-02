package com.isdintership.epe.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.isdintership.epe.entity.AssessmentInformation;
import com.isdintership.epe.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class AssessmentInformationDto {

    private String assessmentTitle;
    private String assessmentId;
    private String evaluatedUserId;
    private String evaluatedUserFullName;
    private Status status;
    private Status currentStatus;
    private String performedOnUser;
    private String performedByUser;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime performedTime;
    private String reason;
    private List<String> feedbackAuthorsIds = new ArrayList<>();


    public AssessmentInformationDto(AssessmentInformation assessmentInformation){
        this.assessmentTitle = assessmentInformation.getAssessmentTitle();
        this.assessmentId = assessmentInformation.getAssessmentId();
        this.evaluatedUserId = assessmentInformation.getEvaluatedUserId();
        this.evaluatedUserFullName = assessmentInformation.getEvaluatedUserFullName();
        if (assessmentInformation.getPerformedByUser() != null) {
            this.performedByUser = assessmentInformation.getPerformedByUser().getFirstname() + " " +assessmentInformation.getPerformedByUser().getLastname();
        } else {
            this.performedByUser = "null";
        }
        if (assessmentInformation.getPerformedOnUser() != null) {
            this.performedOnUser = assessmentInformation.getPerformedOnUser().getFirstname() + " " + assessmentInformation.getPerformedOnUser().getLastname();
        } else {
            this.performedOnUser = "null";
        }
        this.status = assessmentInformation.getStatus();
        this.performedTime = assessmentInformation.getPerformedTime();
        this.reason = assessmentInformation.getReason();
    }
}
