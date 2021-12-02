package com.isdintership.epe.service.impl;

import com.isdintership.epe.service.JobService;
import com.isdintership.epe.dto.JobDto;
import com.isdintership.epe.entity.Job;
import com.isdintership.epe.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * {@code JobServiceImpl} is a service class that implements the {@code JobService} interface.
 *
 * <p> This class provides a few methods to manipulate the job records in the data source
 *
 * @author  Adrian Girlea
 * @since   1.0
 */
@RequiredArgsConstructor
@Service
@Slf4j
class JobServiceImpl implements JobService {

    /**
     * instance of {@code JobRepository},
     * which is a {@code JpaRepository} and controls access to the database
     */
    private final JobRepository jobRepository;


    /**
     * returns all records from data source as a list of {@code JobDto} objects
     * @return a list of {@code JobDto}
     * @since 1.0
     */
    @Override
    public List<JobDto> getAllJobs() {
        log.info("Getting the job list");
        return jobRepository.findAll()
                .stream()
                    .map(job -> new JobDto(job.getJobTitle()))
                .collect(Collectors.toList());
    }

    /**
     * inserts a new record in the data source
     * @param jobDto a {@code JobDto} object that will be inserted into the data source
     * @return a success message confirming the new record was created
     * @since 1.0
     */
    @Override
    public String addJob(JobDto jobDto) {
        jobRepository.save(new Job(jobDto.getTitle()));
        log.info("Successfully added new job");
        return "Job successfully added";
    }
}
