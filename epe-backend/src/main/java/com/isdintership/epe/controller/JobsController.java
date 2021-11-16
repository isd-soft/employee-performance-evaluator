package com.isdintership.epe.controller;


import com.isdintership.epe.service.JobService;
import com.isdintership.epe.dto.JobDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

import static com.isdintership.epe.entity.RoleEnum.Fields.*;

@RestController
@RequestMapping("api/jobs")
@RequiredArgsConstructor
public class JobsController {

    private final JobService jobService;


    @GetMapping
    @RolesAllowed({ROLE_ADMIN, ROLE_USER, ROLE_SYSADMIN})
    public List<JobDto> getAllJobs(){
        return jobService.getAllJobs();
    }

    @PostMapping
    @RolesAllowed({ROLE_ADMIN, ROLE_SYSADMIN})
    public String addJob(@RequestBody JobDto jobDto){
        return jobService.addJob(jobDto);
    }
}
