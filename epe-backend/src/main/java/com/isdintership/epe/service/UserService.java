package com.isdintership.epe.service;

import com.isdintership.epe.dto.LoginRequest;
import com.isdintership.epe.dto.RegistrationRequest;
import com.isdintership.epe.dto.*;

import java.io.IOException;
import java.util.List;

public interface UserService {
    UserDto register(RegistrationRequest user);

    UserDto login (LoginRequest signInRequest);

    UserDto createUser(RegistrationRequest user);

    List<UserDto> getAllUsers();

    UserDto getUserById(String id);

    List<TeamDto> getTeamsByUserId(String id);

    UserDto deleteUser(String id);

    List<JobsDto> getJobTitles();

    PasswordView changePassword(PasswordView passwordView, String id);

    RoleView changeGroup(RoleView roleView, String id);

    List<AssignedUserDto> getAssignedUsers(String id);

    UserDto updateUser(UserDto userDto, String id);

    List<UserDto> getAllBuddies(String id);

    CountDto countAll();
    CountDto countAllBuddies();
    NewUsersThisYearDto countNewUsersThisYear();
}
