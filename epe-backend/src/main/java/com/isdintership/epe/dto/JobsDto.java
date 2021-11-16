package com.isdintership.epe.dto;

import com.isdintership.epe.entity.Job;
import lombok.Data;

@Data
public class JobsDto {
    private String jobTitle;

    public static JobsDto fromJob(Job job) {
        JobsDto jobsDto = new JobsDto();
        jobsDto.setJobTitle(job.getJobTitle());

        return jobsDto;
    }
}
