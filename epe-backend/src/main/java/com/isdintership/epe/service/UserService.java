package com.isdintership.epe.service;

import com.isdintership.epe.dto.*;

import java.util.List;
import java.util.Optional;

public interface UserService {
    String register(RegistrationRequest user);

    UserView login (LoginRequest signInRequest);

    UserView createUser(RegistrationRequest user);

    List<UserView> getAllUsers();

    UserView getUserById(String id);

    UserView updateUser(UserView userView, String id);

    String deleteUser(String id);

    List<JobsDto> getJobTitles();

    List<AssignedUserDto> getAssignedUsers(String id);
}
