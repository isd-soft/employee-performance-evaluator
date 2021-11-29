package com.isdintership.epe.repository;

import com.isdintership.epe.entity.Role;
import com.isdintership.epe.entity.RoleEnum;
import com.isdintership.epe.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
    List<User> findAll();
    Optional<User> findById(String id);
    List<User> findByBuddyId(String id);
    Optional<User> removeById(String id);
    List<User> findByRoleIdIn(List<Integer> roles);
}
