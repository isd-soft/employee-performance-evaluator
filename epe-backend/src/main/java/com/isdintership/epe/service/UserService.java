package com.isdintership.epe.service;

import com.isdintership.epe.dto.*;

import java.io.IOException;
import java.util.List;

public interface UserService {
    SuccessResponse register(RegistrationRequest user);

    UserView login (LoginRequest signInRequest);

    UserView createUser(RegistrationRequest user);

    List<UserView> getAllUsers();

    UserView getUserById(String id);

    UserView updateUser(UserView userView, String id);

    SuccessResponse deleteUser(String id);

    PasswordView changePassword(PasswordView passwordView, String id);

    //    UserView findByEmail(String email);

    RoleView changeGroup(RoleView roleView, String id);

    ImageEditView uploadImage(ImageEditView imageEditView, String id) throws IOException;
}
