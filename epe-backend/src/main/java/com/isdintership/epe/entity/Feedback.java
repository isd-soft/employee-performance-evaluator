package com.isdintership.epe.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "feedbacks")
public class Feedback extends BaseEntity {

    @Column(name = "author_id", nullable = false)
    private String authorId;

    @Column(name = "author_full_name", nullable = false)
    private String authorFullName;

    @ManyToOne(fetch = FetchType.LAZY)
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

    public Feedback() {
    }

    public Feedback(String authorId, String authorFullName, Assessment assessment, String context) {
        this.authorId = authorId;
        this.authorFullName = authorFullName;
        this.assessment = assessment;
        this.context = context;
    }
}
