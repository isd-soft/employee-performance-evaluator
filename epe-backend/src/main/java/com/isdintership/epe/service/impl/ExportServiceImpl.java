package com.isdintership.epe.service.impl;

import com.isdintership.epe.dto.UserDto;
import com.isdintership.epe.entity.User;
import com.isdintership.epe.exception.UserNotFoundException;
import com.isdintership.epe.repository.UserRepository;
import com.isdintership.epe.service.ExportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;



@Service
@Slf4j
@RequiredArgsConstructor
class ExportServiceImpl implements ExportService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDto exportToPdf(String id, HttpServletResponse response) throws IOException {
        User user = userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("User with" + id + " was not found"));
        return UserDto.fromUser(user);
    }
}
