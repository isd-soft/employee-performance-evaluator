package com.isdintership.epe.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "jobs")
@NoArgsConstructor
public class Job extends BaseEntity{

    @Column(name = "job_title", nullable = false)
    private String jobTitle;

    public Job(String jobPosition) {
        this.jobTitle = jobPosition;
    }


}
