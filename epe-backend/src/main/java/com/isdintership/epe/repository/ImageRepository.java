package com.isdintership.epe.repository;

import com.isdintership.epe.entity.Image;
import com.isdintership.epe.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, User> {

}
