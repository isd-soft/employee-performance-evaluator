package com.isdintership.epe.repository;

import com.isdintership.epe.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, String> {

    Optional<Team> findById(String id);

    Optional<Team> findByName(String name);

    List<Team> findAll();

    void deleteById(String id);

}