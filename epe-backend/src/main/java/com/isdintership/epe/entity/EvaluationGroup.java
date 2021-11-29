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
    private Assessment assessment;

    @Column(name = "title", nullable = false)
    private String title;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "evaluation_group_id")
    @Fetch(value = FetchMode.SUBSELECT)
    private List<EvaluationField> evaluationFields = new ArrayList<>();

    @Column(name = "overall_score", columnDefinition = "NUMERIC(5,2)")
    private Float overallScore;

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

    public EvaluationGroup(Assessment assessment, String title, List<EvaluationField> evaluationFields, Float overallScore) {
        this.assessment = assessment;
        this.title = title;
        this.evaluationFields = evaluationFields;
        this.overallScore = overallScore;
    }

    @Override
    public String toString() {
        return "EvaluationGroup{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", evaluationFields=" + evaluationFields +
                ", overallScore=" + overallScore +
                '}';
    }
}
