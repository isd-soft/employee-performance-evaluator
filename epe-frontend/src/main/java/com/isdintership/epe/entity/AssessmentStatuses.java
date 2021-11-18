package com.isdintership.epe.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "assessment_statuses")
public class AssessmentStatuses extends BaseEntity {

    @Column(nullable = false)
    private String status;

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public AssessmentStatuses() {
    }

    public AssessmentStatuses(String status) {
        this.status = status;
    }
}
