package com.isdintership.epe.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "assessment_statuses")
public class AssessmentStatuses extends BaseEntity {

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    @JoinColumn(name = "status_id")
    private List<Assessment> assessment;

    @Column(nullable = false)
    private String status;

    public AssessmentStatuses() {
    }

    public AssessmentStatuses(List<Assessment> assessment, String status) {
        this.assessment = assessment;
        this.status = status;
    }
}
