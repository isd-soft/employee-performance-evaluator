package com.isdintership.epe.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "job")
@NoArgsConstructor
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "job_title", nullable = false)
    private String jobTitle;

    public Job(String jobPosition) {
        this.jobTitle = jobPosition;
    }


}
