package com.isdintership.epe.service;

import com.isdintership.epe.dto.LoginRequest;
import com.isdintership.epe.dto.RegistrationRequest;
import com.isdintership.epe.dto.SuccessResponse;
import com.isdintership.epe.dto.UserView;

import java.util.List;

public interface UserService {
    SuccessResponse register(RegistrationRequest user);

    UserView login (LoginRequest signInRequest);

    UserView createUser(RegistrationRequest user);

    List<UserView> getAllUsers();

    UserView getUserById(String id);

    UserView updateUser(UserView userView, String id);

    SuccessResponse deleteUser(String id);

    //    UserView findByEmail(String email);

}
