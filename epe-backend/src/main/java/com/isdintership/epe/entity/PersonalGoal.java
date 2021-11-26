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
    private Assessment assessment;

    @Column(name = "goal_s_part", nullable = false)
    private String goalSPart;

    @Column(name = "goal_m_part", nullable = false)
    private String goalMPart;

    @Column(name = "goal_a_part", nullable = false)
    private String goalAPart;

    @Column(name = "goal_r_part", nullable = false)
    private String goalRPart;

    @Column(name = "goal_t_part", nullable = false)
    private String goalTPart;

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

    public PersonalGoal(Assessment assessment, String goalSPart, String goalMPart,
                        String goalAPart, String goalRPart, String goalTPart) {
        this.assessment = assessment;
        this.goalSPart = goalSPart;
        this.goalMPart = goalMPart;
        this.goalAPart = goalAPart;
        this.goalRPart = goalRPart;
        this.goalTPart = goalTPart;
    }
}
