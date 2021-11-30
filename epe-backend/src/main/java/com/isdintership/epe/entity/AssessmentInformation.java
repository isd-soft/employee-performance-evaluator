package com.isdintership.epe.entity;

import com.isdintership.epe.dto.AssessmentInformationDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "assessments_information")
public class AssessmentInformation extends BaseEntity{

    private String assessmentTitle;
    @Enumerated(EnumType.STRING)
    private Status status;
    @OneToOne
    private User performedOnUser;
    @OneToOne
    private User performedByUser;
    private LocalDateTime performedTime;
    private String reason;
    public AssessmentInformation(){

    }
}
