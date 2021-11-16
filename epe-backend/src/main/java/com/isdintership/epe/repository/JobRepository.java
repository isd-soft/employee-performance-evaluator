package com.isdintership.epe.repository;

import com.isdintership.epe.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {

    Optional<Job> findByJobTitle(String jobTitle);

    List<Job> findAll();

}
