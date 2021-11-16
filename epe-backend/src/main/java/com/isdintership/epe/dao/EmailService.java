package com.isdintership.epe.dao;

import com.isdintership.epe.entity.User;

public interface EmailService {
    void sendEmail(User user);
}
