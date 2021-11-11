package com.isdintership.epe.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.*;

@Setter
@Getter
@Entity
@Table(name = "assessment")
public class Assessment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @JoinColumn(name = "job_id")
    @ManyToOne
    private Job job;

    @Column(name = "overall_score")
    private Float overallScore;

    @ManyToOne
    private AssessmentStatuses status;

    @Column(name = "is_template")
    private Boolean isTemplate;

    @Column(name = "start_date")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date startDate;

    @Column(name = "end_date")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date endDate;

    @OneToMany(
            mappedBy = "assessment",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    @Fetch(value = FetchMode.SUBSELECT)
    private List<EvaluationGroup> evaluationGroups = new ArrayList<>();

    @OneToMany(
            mappedBy = "assessment",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    @Fetch(value = FetchMode.SUBSELECT)
    private List<PersonalGoal> personalGoals = new ArrayList<>();

    @OneToMany(
            mappedBy = "assessment",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    @Fetch(value = FetchMode.SUBSELECT)
    private List<DepartmentGoal> departmentGoals = new ArrayList<>();

    @OneToMany(
            mappedBy = "assessment",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Feedback> feedbacks = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public Assessment() {
    }

    public Assessment(User user, String title, String description, Job job, Float overallScore,
                      AssessmentStatuses status, Boolean isTemplate, Date startDate, Date endDate,
                      List<EvaluationGroup> evaluationGroups, List<PersonalGoal> personalGoals,
                      List<DepartmentGoal> departmentGoals, List<Feedback> feedbacks) {
        this.user = user;
        this.title = title;
        this.description = description;
        this.job = job;
        this.overallScore = overallScore;
        this.status = status;
        this.isTemplate = isTemplate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.evaluationGroups = evaluationGroups;
        this.personalGoals = personalGoals;
        this.departmentGoals = departmentGoals;
        this.feedbacks = feedbacks;
    }
}
