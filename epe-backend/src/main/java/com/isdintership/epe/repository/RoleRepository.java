package com.isdintership.epe.repository;

import com.isdintership.epe.entity.Role;
import com.isdintership.epe.entity.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByRole(RoleEnum name);

}
