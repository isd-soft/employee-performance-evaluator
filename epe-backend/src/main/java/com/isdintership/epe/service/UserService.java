package com.isdintership.epe.service;

import com.isdintership.epe.dto.*;

import java.util.List;

public interface UserService {
    String register(RegistrationRequest user);

    UserDto login (LoginRequest signInRequest);

    UserDto createUser(RegistrationRequest user);

    List<UserDto> getAllUsers();

    UserDto getUserById(String id);

    UserDto updateUser(UserDto userDto, String id);

    String deleteUser(String id);

    List<JobsDto> getJobTitles();

    List<AssignedUserDto> getAssignedUsers(String id);
}
