package com.isdintership.epe.repository;

import com.isdintership.epe.entity.Image;
import com.isdintership.epe.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, String> {
    Optional<Image> findByUserId(String id);
    //String deleteByUserId(String userId);
}
