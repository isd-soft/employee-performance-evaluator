package com.isdintership.epe.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "evaluation_field")
public class EvaluationField extends BaseEntity {

    @Column(name = "title", nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    private EvaluationGroup evaluationGroup;

    @Column(name = "first_score")
    private Integer firstScore;

    @Column(name = "second_score")
    private Integer secondScore;

    @Column(name = "overall_score")
    private Integer overallScore;

    @Column(name = "comment")
    private String comment;

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public EvaluationField() {
    }

    public EvaluationField(String title, EvaluationGroup evaluationGroup,
                           Integer firstScore, Integer secondScore,
                           Integer overallScore, String comment) {
        this.title = title;
        this.evaluationGroup = evaluationGroup;
        this.firstScore = firstScore;
        this.secondScore = secondScore;
        this.overallScore = overallScore;
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "EvaluationField{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", firstScore=" + firstScore +
                ", secondScore=" + secondScore +
                ", overallScore=" + overallScore +
                ", comment='" + comment + '\'' +
                '}';
    }
}
