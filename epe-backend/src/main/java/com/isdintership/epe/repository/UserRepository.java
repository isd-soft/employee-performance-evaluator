package com.isdintership.epe.repository;

import com.isdintership.epe.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
    List<User> findAll();
    Optional<User> findById(String id);
    List<User> findByTeamLeaderIdOrBuddyId(String teamLeaderId, String buddyId);
    Optional<User> removeById(String id);
}
