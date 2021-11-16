package com.isdintership.epe.service_implement;

import com.isdintership.epe.dto.LoginRequest;
import com.isdintership.epe.dto.RegistrationRequest;
import com.isdintership.epe.dto.AssignedUserDto;
import com.isdintership.epe.dto.UserView;
import com.isdintership.epe.entity.*;
import com.isdintership.epe.exception.JobNotFoundException;
import com.isdintership.epe.exception.UserExistsException;
import com.isdintership.epe.exception.UserNotFoundException;
import com.isdintership.epe.repository.*;
import com.isdintership.epe.security.jwt.JwtTokenProvider;
import com.isdintership.epe.dao.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final JobRepository jobRepository;
    private final AssessmentRepository assessmentRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public String register(RegistrationRequest request) {
        Optional<User> byEmail = userRepository.findByEmail(request.getEmail());
        if (byEmail.isPresent()) {
            throw new UserExistsException("User with email " + request.getEmail()
                    + " already exists");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setBirthDate(request.getBirthDate());
        user.setEmploymentDate(request.getEmploymentDate());
        user.setPhoneNumber(request.getPhoneNumber());

        user.setBio(request.getBio());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Role roleUser = roleRepository.findByRole(RoleEnum.ROLE_USER);
        user.setRole(roleUser);

        Job jobUser = jobRepository.findByJobTitle(request.getJob()).orElseThrow(() ->
                        new JobNotFoundException("Job with name " + request.getJob() + " not found"));
        user.setJob(jobUser);

        log.info("Saving user {}", request.getEmail());
        userRepository.save(user);

        return "Registration successful";

    }
    
    @Override
    @Transactional
    public UserView login(LoginRequest signInRequest) {
        String email = signInRequest.getEmail();
        String password = signInRequest.getPassword();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new UserNotFoundException("User with email " + email + "was not found")
        );

        String token = jwtTokenProvider.createToken(user);

        UserView response = UserView.fromUser(user);
        response.setToken(token);

        return response;
    }

    @Override
    public UserView createUser(RegistrationRequest request) {
        Optional<User> byEmail = userRepository.findByEmail(request.getEmail());
        if (byEmail.isPresent()) {
            throw new UserExistsException("User with email " + request.getEmail()
                    + " already exists");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setBirthDate(request.getBirthDate());
        user.setEmploymentDate(request.getEmploymentDate());
        user.setPhoneNumber(request.getPhoneNumber());

        user.setBio(request.getBio());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Role roleUser = roleRepository.findByRole(RoleEnum.ROLE_USER);
        user.setRole(roleUser);

        Job jobUser = jobRepository.findByJobTitle(request.getJob()).orElseThrow(() ->
                new JobNotFoundException("Job with name " + request.getJob() + " not found"));
        user.setJob(jobUser);

        log.info("Saving user {}", request.getEmail());

        return (UserView.fromUser(userRepository.save(user)));
    }

    @Override
    @Transactional
    public List<UserView> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserView> userViews = new ArrayList<>();
        for (User user : users) {
            userViews.add(UserView.fromUser(user));
        }

        return userViews;
    }

    @Override
    @Transactional
    public Optional<UserView> getUserById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow( () -> new UserNotFoundException("The user with this id does not exist"));
        return Optional.ofNullable(UserView.fromUser(user));
    }

    @Override
    @Transactional
    public UserView updateUser(UserView userView, String id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("User with id " + id + "was not found"));

        if (userView.getBuddyId() != null) {
            userRepository.findById(userView.getBuddyId()).orElseThrow(() ->
                new UserNotFoundException("Buddy with id " + id + " was not found"));
            user.setBuddyId(userView.getBuddyId());
        }

        user.setEmail(userView.getEmail());
        user.setFirstname(userView.getFirstname());
        user.setLastname(userView.getLastname());
        user.setBirthDate(userView.getBirthDate());
        user.setEmploymentDate(userView.getEmploymentDate());
        user.setPhoneNumber(userView.getPhoneNumber());

        Job job = jobRepository.findByJobTitle(userView.getJob()).orElseThrow(() ->
                new JobNotFoundException("Job with name " + userView.getJob() + " not found"));;
        user.setJob(job);
        user.setBio(userView.getBio());

        return userView;
    }

    @Override
    @Transactional
    public String deleteUser(String id) {
        userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("User with id " + id + " was not found"));
        userRepository.removeById(id);

        return ("User with id " + id + " was deleted");
    }

    @Override
    @Transactional
    public List<AssignedUserDto> getAssignedUsers(String id) {

        User user = userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("User with id " + id + " was not found"));

        List<User> assignedUsers = userRepository.findByBuddyId(id);
        Optional<Team> team = teamRepository.findByTeamLeaderId(id);
        if (team.isPresent()) {
            assignedUsers.addAll(team.get().getMembers());
        }

        List<AssignedUserDto> assignedUserDto = new ArrayList<>();
        assignedUsers.forEach(user -> {
            assignedUserDto.add(AssignedUserDto.fromUserTo(user,
                    assessmentRepository.findByUserId(user.getId())));
        });

        return assignedUserDto;
    }
}
