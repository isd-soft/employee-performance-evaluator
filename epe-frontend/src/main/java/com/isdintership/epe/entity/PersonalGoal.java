package com.isdintership.epe.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "personal_goals")
public class PersonalGoal extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assessment_id")
    private Assessment assessment;

    @Column(name = "context", nullable = false)
    private String context;

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public PersonalGoal() {
    }

    public PersonalGoal(Assessment assessment, String context) {
        this.assessment = assessment;
        this.context = context;
    }
}
