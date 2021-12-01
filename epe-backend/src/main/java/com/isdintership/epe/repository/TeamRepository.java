package com.isdintership.epe.repository;

import com.isdintership.epe.entity.Team;
import com.isdintership.epe.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, String> {

    Optional<Team> findById(String id);
    List<Team> findByTeamLeaderId(String id);
    Optional<Team> findByName(String name);
    List<Team> findAll();
    void deleteById(String id);
    List<Team> findAllByTeamLeaderId(String id);
}