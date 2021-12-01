package com.isdintership.epe.service.impl;

import com.isdintership.epe.service.JobService;
import com.isdintership.epe.dto.JobDto;
import com.isdintership.epe.entity.Job;
import com.isdintership.epe.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * {@code JobServiceImpl} is a service class that implements the {@code JobService} interface.
 * It has only one field, named {@code jobRepository} of type {@code JobRepository}
 *
 * <p> This class provides a few methods to manipulate the records in the data source
 *
 * @author  Adrian Girlea
 * @since   1.0
 */
@RequiredArgsConstructor
@Service
class JobServiceImpl implements JobService {

    /**
     * {@code jobRepository} is an instance of {@code JobRepository}
     * that controls the access to the database, job table
     */
    private final JobRepository jobRepository;


    /**
     * This method retrieves all records from the job table and
     * returns them as a list of {@code JobDto}
     */
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
