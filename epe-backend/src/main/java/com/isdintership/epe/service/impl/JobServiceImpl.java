package com.isdintership.epe.service.impl;

import com.isdintership.epe.service.JobService;
import com.isdintership.epe.dto.JobDto;
import com.isdintership.epe.entity.Job;
import com.isdintership.epe.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    @Override
    public List<JobDto> getAllJobs() {
        return jobRepository.findAll()
                .stream()
                    .map(job -> new JobDto(job.getJobTitle()))
                .collect(Collectors.toList());
    }

    @Override
    public String addJob(JobDto jobDto) {
        jobRepository.save(new Job(jobDto.getTitle()));
        return "Job successfully added";
    }
}
