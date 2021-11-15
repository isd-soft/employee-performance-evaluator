package com.isdintership.epe.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Setter
@Getter
@Entity
@Table(name = "assessment")
public class Assessment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
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

    @ManyToOne(fetch = FetchType.LAZY)
    private AssessmentStatuses status;

    @Column(name = "is_template")
    private Boolean isTemplate;

    @Column(name = "start_date")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDate startDate;

    @Column(name = "end_date")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDate endDate;

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinColumn(name = "assessment_id")
    private List<EvaluationGroup> evaluationGroups = new ArrayList<>();

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinColumn(name = "assessment_id")
    private List<PersonalGoal> personalGoals = new ArrayList<>();

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinColumn(name = "assessment_id")
    private List<DepartmentGoal> departmentGoals = new ArrayList<>();

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinColumn(name = "assessment_id")
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
                      AssessmentStatuses status, Boolean isTemplate, LocalDate startDate, LocalDate endDate,
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

    @Override
    public String toString() {
        return "Assessment{" +
                "user=" + user +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", job=" + job +
                ", overallScore=" + overallScore +
                ", status=" + status +
                ", isTemplate=" + isTemplate +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", evaluationGroups=" + evaluationGroups +
                ", personalGoals=" + personalGoals +
                ", departmentGoals=" + departmentGoals +
                ", feedbacks=" + feedbacks +
                '}';
    }
}
