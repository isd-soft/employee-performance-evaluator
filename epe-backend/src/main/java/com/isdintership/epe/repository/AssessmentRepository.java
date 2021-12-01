package com.isdintership.epe.repository;

import com.isdintership.epe.entity.Assessment;
import com.isdintership.epe.entity.Status;
import com.isdintership.epe.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AssessmentRepository extends JpaRepository<Assessment, String> {

    Optional<Assessment> findById(String id);
    List<Assessment> findByUser(User user);
    List<Assessment> findByUserAndStatus(User user, Status status);
    List<Assessment> findByUserAndStatusIn(User user, List<Status> statuses);
    List<Assessment> findAllByIsTemplate(Boolean isTemplate);
    Optional<Assessment> findByTitleAndIsTemplate(String title, Boolean isTemplate);
    Optional<Assessment> findByTitleAndUser(String title, User user);
    Optional<Assessment> findByIdAndIsTemplate(String id, Boolean isTemplate);
    List<Assessment> findByUserInAndStatusIn(List<User> users, List<Status> statuses);
    Long countAllByStartDateBetween(LocalDateTime fromDate, LocalDateTime toDate);

    void removeById(String id);
}
