package com.isdintership.epe.service;

import com.isdintership.epe.dto.JobDto;
import com.isdintership.epe.entity.Job;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JobService {
    List<JobDto> getAllJobs();
    String addJob(JobDto jobDto);
}
