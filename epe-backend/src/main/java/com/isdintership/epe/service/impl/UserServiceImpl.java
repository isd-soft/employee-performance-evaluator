package com.isdintership.epe.service.impl;


import com.isdintership.epe.dto.*;
import com.isdintership.epe.entity.*;
import com.isdintership.epe.exception.*;

import com.isdintership.epe.repository.*;
import com.isdintership.epe.security.jwt.JwtTokenProvider;
import com.isdintership.epe.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * {@code UserServiceImpl} is a service class that implements the {@code UserService} interface.
 *
 * <p> This class manages all the requests related to users, authentication and registration
 *
 * @author Maxim Gribencicov
 * @author Andrei Chetrean
 * @author Adrian Girlea
 * @author Nicolae Morari
 * @author Colin Timofei
 * @since 1.0
 */
@Service
@Slf4j
@RequiredArgsConstructor
class UserServiceImpl implements UserService {

    /**
     * {@code JpaRepository} that handles the access to the user table
     */
    private final UserRepository userRepository;

    /**
     * {@code JpaRepository} that handles the access to the team table
     */
    private final TeamRepository teamRepository;

    /**
     * {@code JpaRepository} that handles the access to the job table
     */
    private final JobRepository jobRepository;

    /**
     * {@code JpaRepository} that handles the assessment to the user table
     */
    private final AssessmentRepository assessmentRepository;

    /**
     * {@code JpaRepository} that handles the access to the role table
     */
    private final RoleRepository roleRepository;

    /**
     * instance of {@code PasswordEncoder} that uses the BCrypt strong hashing function
     */
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * instance of {@code JwtTokenProvider} that handles JWT creation, validation and decoding
     */
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * instance of {@code AuthenticationManager} that manages the authentication modules that an application uses
     */
    private final AuthenticationManager authenticationManager;


    /**
     * creates a user record in the data source using the details provided in the {@code RegistrationRequest}
     * object and generates a JWT token for the new user, if no errors
     * @param request {@code RegistrationRequest} object containing all the new user details
     * @return new user details and the generated token
     * @throws {@code UserExistsException} if a user with the provided email already exists
     * @throws {@code JobNotFoundException} if a job with the provided job name doesn't exist in the database
     * @since 1.0
     */
    @Override
    @Transactional
    public UserDto register(RegistrationRequest request) {
        Optional<User> byEmail = userRepository.findByEmail(request.getEmail());
        if (byEmail.isPresent()) {
            log.error("User with email " + request.getEmail() + " already exists");
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

        user.setRegistrationDate(LocalDateTime.now());

        Role roleUser = roleRepository.findByRole(RoleEnum.ROLE_USER);
        user.setRole(roleUser);

        Job jobUser = jobRepository.findByJobTitle(request.getJob()).orElseThrow(() ->
                new JobNotFoundException("Job with name " + request.getJob() + " not found"));
        user.setJob(jobUser);

        File imageSourceFile = new File("./epe-backend/userDefaultImage.png");

        try {
            user.setImageBytes(encodeImageFromFile(imageSourceFile));
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.info("Saving user {}", request.getEmail());
        userRepository.save(user);

        return UserDto.fromUser(user);

    }

    /**
     * checks if a user with provided email and password exists in the data source and returns
     * all user details and a new JWT token
     * @param signInRequest {@code LoginRequest} object containing all the new user details
     * @return a {@code UserDto} with user details
     * @throws {@code AuthenticationException} if the provided details are not valid
     * @throws {@code UserNotFoundException} if a user with the provided details doesn't exist in the database
     * @since 1.0
     */
    @Override
    @Transactional
    public UserDto login(LoginRequest signInRequest) {
        String email = signInRequest.getEmail();
        String password = signInRequest.getPassword();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        User user = userRepository.findByEmail(email).orElseThrow(() ->{
                log.error("User with email " + email + "was not found");
                return new UserNotFoundException("User with email " + email + "was not found");
                }
        );

        String token = jwtTokenProvider.createToken(user);

        UserDto response = UserDto.fromUser(user);
        response.setToken(token);
        log.info("Logging user {}", user.getEmail());
        return response;
    }

    /**
     * creates a new record in the data source, without generating a token for the new user
     * @param request {@code LoginRequest} object containing user's email and password
     * @return a {@code UserDto} with user details and the new generated token
     * @throws {@code UserExistsException} if a user with the provided email already exists
     * @throws {@code JobNotFoundException} if a job with the provided job name doesn't exist in the database
     * @throws {@code IOException} if an I/O exception occurs while inserting the image in the data source
     * @since 1.0
     */
    @Override
    public UserDto createUser(RegistrationRequest request) {
        Optional<User> byEmail = userRepository.findByEmail(request.getEmail());
        if (byEmail.isPresent()) {
            log.error("User with email " + request.getEmail()
                    + " already exists");
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

        Job jobUser = jobRepository.findByJobTitle(request.getJob()).orElseThrow(() ->{
            log.error("Job with name " + request.getJob() + " not found");
            return new JobNotFoundException("Job with name " + request.getJob() + " not found");
        });
        user.setJob(jobUser);

        File imageSourceFile = new File("../epe-backend//userDefaultImage.png");

        try {
            user.setImageBytes(encodeImageFromFile(imageSourceFile));
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.info("Saving user {}", request.getEmail());

        return (UserDto.fromUser(userRepository.save(user)));
    }

    /**
     * returns a list containing all users from the data source
     * @return a list of {@code UserDto} objects, created from user records
     * @since 1.0
     */
    @Override
    @Transactional
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(UserDto.fromUser(user));
        }
        log.info("Getting all users");
        return userDtos;
    }

    /**
     * returns a list of users that do not have the same ID as the provided one
     * @param id {@code String} that will be used as user's ID
     * @return a list of {@code UserDto} that can be set as current user's buddy
     * @since 1.0
     */
    @Override
    @Transactional
    public List<UserDto> getAllBuddies(String id) {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            if (!user.getId().equals(id)) {
                userDtos.add(UserDto.fromUser(user));
            }
        }
        log.info("Getting {}'s buddies", userRepository.findById(id));
        return userDtos;
    }

    /**
     * retrieves all the details of the user with the provided ID and returns them as a {@code UserDto} object
     * @param id {@code String} that will be used as user's ID
     * @return all user details where the ID matches the provided one
     * @throws {@code UserNotFoundException} if a user with the provided ID was not found
     * @since 1.0
     */
    @Override
    @Transactional
    public UserDto getUserById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("The user with this {} does not exist", id);
                    return new UserNotFoundException("The user with this id does not exist");
                });
        log.info("Getting user with id {}", id);
        return UserDto.fromUser(user);
    }

    /**
     * updates user record with ID that matches the provided one
     * new user details are stored in the {@code userDto} object
     * @param id {@code String} that will be used as user's ID
     * @param userDto {@code UserDto} that contains new user details
     * @return updated user details
     * @throws {@code UserNotFoundException} if a user with the provided ID was not found
     * @throws {@code UserNotFoundException} if a user with provided buddy ID was not found
     * @throws {@code JobNotFoundException} if a job with the provided name was not found in the job table
     * @throws {@code RoleNotFoundException} if the provided role doesn't match any record from data source
     * @since 1.0
     */
    @Override
    @Transactional
    public UserDto updateUser(UserDto userDto, String id) {
        User user = userRepository.findById(id).orElseThrow(() ->{
            log.error("User with id " + id + "was not found");
            return new UserNotFoundException("User with id " + id + "was not found");
        });

        if (userDto.getBuddyId() != null) {
            userRepository.findById(userDto.getBuddyId()).orElseThrow(() ->{
                log.error("Buddy with id " + id + " was not found");
                return  new UserNotFoundException("Buddy with id " + id + " was not found");
            });
            user.setBuddyId(userDto.getBuddyId());
        }

        if (userDto.getEmail() != null) {
            user.setEmail(userDto.getEmail());
        }
        if (userDto.getFirstname() != null) {
            user.setFirstname(userDto.getFirstname());
        }
        if (userDto.getLastname() != null) {
            user.setLastname(userDto.getLastname());
        }
        if (String.valueOf(userDto.getBirthDate()) != null) {
            user.setBirthDate(userDto.getBirthDate());
        }
        if (String.valueOf(userDto.getEmploymentDate()) != null) {
            user.setEmploymentDate(userDto.getEmploymentDate());
        }
        if (userDto.getPhoneNumber() != null) {
            user.setPhoneNumber(userDto.getPhoneNumber());
        }
        if (userDto.getJob() != null) {
            Job job = jobRepository.findByJobTitle(userDto.getJob()).orElseThrow(() ->{
                log.error("Job with name " + userDto.getJob() + " not found");
                return new JobNotFoundException("Job with name " + userDto.getJob() + " not found");
            });
            user.setJob(job);
        }
        if (userDto.getBio() != null) {
            user.setBio(userDto.getBio());
        }

        if (userDto.getImage() != null) {
            user.setImageBytes(encodeImageFromString(userDto.getImage()));
        }

        if (userDto.getRole() != null) {
            Map<String,Integer> roles = new HashMap<>();
            roles.put("User",1);
            roles.put("Administrator",2);
            int roleId = roles.get(userDto.getRole());
            Role role = roleRepository.findById(roleId).orElseThrow(() ->{
                log.error("Role with id " + roleId + " was not found");
                return new RoleNotFoundException("Role with id " + roleId + " was not found");
            });
            user.setRole(role);
        }
        log.info("Updating user with the id {}", userDto.getId());
        return userDto;
    }

    /**
     * returns the number of users in the data source
     * @return a {@code UserDto} object that contains the number of users in database
     * @since 1.0
     */
    @Override
    @Transactional
    public CountDto countAll() {
        return new CountDto(userRepository.count());
    }

    @Override
    @Transactional
    public CountDto countAllBuddies() {
        return new CountDto(userRepository.countAllByBuddyIdNotNull());
    }

    /**
     * returns a list with the number of user registered each month, only current year
     * @return a {@code NewUsersThisYearDto} object with the number of registered users
     * @since 1.0
     */
    @Override
    @Transactional
    public NewUsersThisYearDto countNewUsersThisYear() {
        NewUsersThisYearDto newUsers = new NewUsersThisYearDto();
        for (int i = 0; i < 12; i++) {
            LocalDateTime fromDate = LocalDateTime.of(LocalDateTime.now().getYear(), i + 1, 1, 0, 0);
            newUsers.getMonths().add(i, userRepository.countAllByRegistrationDateBetween(fromDate, fromDate.plusMonths(1)));
        }

        return newUsers;
    }

    /**
     * deletes a user from the data source, if a record with the provided ID exists
     * @return a {@code UserDto} object with deleted user details
     * @throws {@code UserNotFoundException} if a user with the provided ID doesn't exist in the database
     * @since 1.0
     */
    @Override
    @Transactional
    public UserDto deleteUser(String id) {

        UserDto userDto = UserDto.fromUser(userRepository.findById(id).orElseThrow(() ->{
            log.error("User with id " + id + " was not found");
            return new UserNotFoundException("User with id " + id + " was not found");
        }));
        userRepository.removeById(id);

        List<User> users = userRepository.findByBuddyId(id);

        for (User user : users) {
            user.setBuddyId(null);
        }
        log.info("Deleted user with id {}", id);
        return userDto;
    }

    /**
     * returns a list of job names from the data source
     * @return a list of {@code JobsDto} objects
     * @since 1.0
     */
    @Override
    @Transactional
    public List<JobsDto> getJobTitles() {
        return jobRepository.findAll().stream()
                .map(JobsDto::fromJob)
                .collect(Collectors.toList());
    }

    /**
     * returns a list of users where the user with provided ID is the team leader or buddy
     * @param id user's ID
     * @return a list of {@code AssignedUserDto} where the user with provided ID is team leader or buddy
     * @throws {@code UserNotFoundException} if a user with the provided ID was not found
     * @since 1.0
     */
    @Override
    @Transactional
    public List<AssignedUserDto> getAssignedUsers(String id) {
        userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("User with id " + id + " was not found"));

        List<User> assignedUsers = userRepository.findByBuddyId(id);
        teamRepository.findByTeamLeaderId(id).forEach(team -> assignedUsers.addAll(team.getMembers()));
        List<AssignedUserDto> assignedUsersDtos = new ArrayList<>();
        assignedUsers.forEach(user -> assignedUsersDtos.add(AssignedUserDto.fromUser(user)));

        return assignedUsersDtos;
    }

    /**
     * returns a list of teams where the user with the provided ID is team leader or team member
     * @param id {@code String} containing team leader's ID
     * @return a list of {@code TeamDto} containing each team details where the user with the provided ID is team leader
     * @throws {@code UserNotFoundException} if a user with the provided ID was not found
     * @throws {@code TeamNotFoundException} if the user's team ID doesn't match any team id in teams table
     * @since 1.0
     */
    @Override
    @Transactional
    public List<TeamDto> getTeamsByUserId(String id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("The user with this {} does not exist", id);
                    return new UserNotFoundException("The user with this id does not exist");
                });

        List<TeamDto> teamDtoList = new ArrayList();

        List<Team> teamList = teamRepository.findAllByTeamLeaderId(id);
        for (Team team: teamList) {
            teamDtoList.add(TeamDto.fromTeam(team));
        }

        if(user.getTeam() != null) {
            Team team = teamRepository.findById(user.getTeam().getId()).orElseThrow(() ->{
                log.error("Team for user with id " + id + " was not found");
                return new TeamNotFoundException("Team for user with id " + id + " was not found");
            });
            teamDtoList.add(TeamDto.fromTeam(team));
        }
        return teamDtoList;
    }

    /**
     * changes the password for the user with the provided ID
     * @param id user's ID
     * @param passwordView {@code PasswordView} containing old and new password
     * @return a {@code PasswordView} if the password was changed
     * @throws {@code UserNotFoundException} if a user with the provided ID was not found
     * @throws {@code BadCredentialsException} if the provided old password doesn't match the one from database
     * @throws {@code BadCredentialsException} if the new password doesn't match the new confirmed password
     * @throws {@code BadCredentialsException} if the old and new password match
     * @since 1.0
     */
    @Override
    @Transactional
    public PasswordView changePassword(PasswordView passwordView, String id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("User with id " + id + "was not found"));

        boolean isPasswordMatched = passwordEncoder.matches(passwordView.getOldPassword(), user.getPassword());

        if (isPasswordMatched) {
            if (!(passwordView.getNewPassword().equals(passwordView.getNewPasswordConfirmation()))) {
                log.error("New password was not confirmed");
                throw new BadCredentialsException("New password was not confirmed");
            } else if (passwordView.getNewPassword().equals(passwordView.getOldPassword())) {
                log.error("Old and new passwords are the same");
                throw new BadCredentialsException("Old and new passwords are the same");
            } else {
                user.setPassword(passwordEncoder.encode(passwordView.getNewPassword()));
            }
        } else {
            log.error("Old password doesn't match with the old inserted password");
            throw new BadCredentialsException("Old password doesn't match with the old inserted password");
        }
        return passwordView;
    }

    /**
     * changes user's role with the provided one
     * @param id user's ID
     * @param roleView {@code RoleView} containing the new role ID
     * @return user's role id, if it was changed
     * @throws {@code UserNotFoundException} if a user with the provided ID was not found
     * @throws {@code RoleNotFoundException} if a role with the provided ID doesn't exist in the data source
     * @since 1.0
     */
    @Override
    @Transactional
    public RoleView changeGroup(RoleView roleView, String id) {
        User user = userRepository.findById(id).orElseThrow(() ->{
            log.error("User with id " + id + " was not found");
            return new UserNotFoundException("User with id " + id + " was not found");
        });

        Role role = roleRepository.findById(roleView.getId()).orElseThrow(() ->{
            log.error("Role with id " + roleView.getId() + " was not found");
            return new RoleNotFoundException("Role with id " + roleView.getId() + " was not found");
        });

        user.setRole(role);
        log.info("Changed the user with id {} group", id);
        return roleView;
    }

    /**
     * encodes the provided file using Base64
     * @param image a {@code File} that should be an image
     * @return encoded image file
     * @throws {@code IOException} if any I/O exception occurred during image reading or encoding
     * @since 1.0
     */
    private static byte[] encodeImageFromFile(File image) throws IOException {
        FileInputStream imageStream = new FileInputStream(image);

        byte[] data = imageStream.readAllBytes();

        String imageString = Base64.getEncoder().encodeToString(data);

        byte[] finalData = imageString.getBytes();
        imageStream.close();

        return finalData;
    }

    /**
     * transforms the provided string into a char array
     * @param imageBase64Encode a string that shall be transformed
     * @return a byte array created from the provided string
     * @since 1.0
     */
    private static byte[] encodeImageFromString(String imageBase64Encode) {
        byte[] finalData = imageBase64Encode.getBytes();
        return finalData;
    }

}


