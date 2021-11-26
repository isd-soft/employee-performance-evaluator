package com.isdintership.epe.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Setter
@Getter
@Entity
@Table(name = "assessments")
public class Assessment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = "title")
    private String title;

    @Column(name = "evaluated_user_full_name")
    private String evaluatedUserFullName;

    @Column(name = "evaluator_full_name")
    private String evaluatorFullName;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @JoinColumn(name = "job_id")
    @ManyToOne
    private Job job;

    @Column(name = "overall_score")
    private Integer overallScore;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "is_template")
    private Boolean isTemplate;

    @Column(name = "start_date")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime endDate;

    @Column(name = "creation_date")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime creationDate;

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    @JoinColumn(name = "assessment_id")
    @Fetch(value = FetchMode.SUBSELECT)
    private List<EvaluationGroup> evaluationGroups = new ArrayList<>();

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    @JoinColumn(name = "assessment_id")
    @Fetch(value = FetchMode.SUBSELECT)
    private List<PersonalGoal> personalGoals = new ArrayList<>();

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    @JoinColumn(name = "assessment_id")
    @Fetch(value = FetchMode.SUBSELECT)
    private List<DepartmentGoal> departmentGoals = new ArrayList<>();

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    @JoinColumn(name = "assessment_id")
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

    public Assessment(User user, String title, String evaluatedUserFullName, String description,
                      Job job, Integer overallScore, Status status, Boolean isTemplate,
                      LocalDateTime startDate, LocalDateTime endDate, LocalDateTime creationDate,
                      List<EvaluationGroup> evaluationGroups, List<PersonalGoal> personalGoals,
                      List<DepartmentGoal> departmentGoals, List<Feedback> feedbacks) {
        this.user = user;
        this.title = title;
        this.evaluatedUserFullName = evaluatedUserFullName;
        this.description = description;
        this.job = job;
        this.overallScore = overallScore;
        this.status = status;
        this.isTemplate = isTemplate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.creationDate = creationDate;
        this.evaluationGroups = evaluationGroups;
        this.personalGoals = personalGoals;
        this.departmentGoals = departmentGoals;
        this.feedbacks = feedbacks;
    }
}
