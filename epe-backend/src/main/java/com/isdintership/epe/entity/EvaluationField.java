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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "evaluation_group_id")
    private EvaluationGroup evaluationGroup;

    @Column(name = "value", nullable = false)
    private Integer value;

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

    public EvaluationField(String title, EvaluationGroup evaluationGroup, Integer value) {
        this.title = title;
        this.evaluationGroup = evaluationGroup;
        this.value = value;
    }
}
