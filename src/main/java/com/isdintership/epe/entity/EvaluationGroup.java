package com.isdintership.epe.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "evaluation_group")
public class EvaluationGroup extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assessment_id")
    private Assessment assessment;

    @Column(name = "title", nullable = false)
    private String title;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "evaluationGroup",
            orphanRemoval = true
    )
    @Fetch(value = FetchMode.SUBSELECT)
    private List<EvaluationField> evaluationFields = new ArrayList<>();

    @Column(name = "overall_score")
    private Integer overallScore;

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public EvaluationGroup() {
    }

    public EvaluationGroup(Assessment assessment, String title, List<EvaluationField> evaluationFields) {
        this.assessment = assessment;
        this.title = title;
        this.evaluationFields = evaluationFields;
    }
}
