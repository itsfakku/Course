package com.btec.cms.service.impl;

import com.btec.cms.common.BaseConstants;
import com.btec.cms.dto.AccountDto;
import com.btec.cms.dto.UserDto;
import com.btec.cms.entity.RoleEntity;
import com.btec.cms.entity.UserEntity;
import com.btec.cms.exception.ResourceAlreadyExistsException;
import com.btec.cms.exception.ResourceNotFoundException;
import com.btec.cms.repository.RoleRepository;
import com.btec.cms.repository.UserRepository;
import com.btec.cms.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final ModelMapper modelMapper;
  private final PasswordEncoder passwordEncoder;
  private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

  @Autowired
  public UserServiceImpl(
      UserRepository userRepository,
      RoleRepository roleRepository,
      ModelMapper modelMapper,
      PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.modelMapper = modelMapper;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public Optional<AccountDto> findByUsername(String username) {
    UserEntity user = userRepository.findByUsername(username);
    log.info("Get account ");
    if (user != null && user.getStatus() == BaseConstants.USER_STATUS_ACTIVE) {
      return Optional.of(modelMapper.map(user, AccountDto.class));
    }
    return Optional.empty();
  }

  @Override
  public List<UserDto> getAllUsers() {
    log.info("Get all users ");
    return userRepository.findAll().stream()
        .filter(
            user ->
                user.getStatus() == BaseConstants.USER_STATUS_ACTIVE
                    || user.getStatus() == BaseConstants.USER_STATUS_NEW)
        .map(user -> modelMapper.map(user, UserDto.class))
        .collect(Collectors.toList());
  }

  @Override
  public UserDto getUserById(Long id) {
    log.info("Get user  detail information by id ");
    return userRepository
        .findById(id)
        .map(user -> modelMapper.map(user, UserDto.class))
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));
  }

  @Override
  public UserDto createUser(UserDto userDto) {

    log.info("Create new user");

    // check blank email
    if (isBlank(userDto.getEmail()))
      throw new ResourceNotFoundException("User email can not be empty");

    if (userRepository.existsByEmail(userDto.getEmail()))
      throw new ResourceAlreadyExistsException("Email", userDto.getEmail());

    String username = generateUsername(userDto.getEmail());
    String encodedPassword = generatePassword(username, userDto.getDob());
    Optional<RoleEntity> roleEntity = roleRepository.findByRole(userDto.getRole());

    UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
    userEntity.setUsername(username);
    userEntity.setPassword(encodedPassword);
    userEntity.setStatus(BaseConstants.USER_STATUS_ACTIVE);
    if (roleEntity.isPresent()) {
      userEntity.setRoleEntity(roleEntity.get());
    } else {
      RoleEntity defaultRole = roleRepository.getById(BaseConstants.ROLE_ID_STUDENT);
      userEntity.setRoleEntity(defaultRole);
    }

    UserEntity createdUser = userRepository.save(userEntity);
    log.info("Created user with email: {}", createdUser.getEmail());


    return modelMapper.map(createdUser, UserDto.class);

  }

  @Override
  public void deleteUser(Long id) {
    log.info("Delete user with id {}  ", id);
    UserEntity user =
        userRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Not found user with id: " + id));

    userRepository.delete(user);
  }

  @Override
  public UserDto updateUser(Long id, UserDto userDto) {
    UserEntity userEntity =
        userRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Not found user with id: " + id));

    Optional<RoleEntity> roleEntity = roleRepository.findByRole(userDto.getRole());
    Instant instant = Instant.now();

    if (roleEntity.isPresent()) {
      userEntity.setRoleEntity(roleEntity.get());
    } else {
      RoleEntity defaultRole = roleRepository.getById(BaseConstants.ROLE_ID_STUDENT);
      userEntity.setRoleEntity(defaultRole);
    }

    userEntity.setAddress(userDto.getAddress());
    userEntity.setAvatar(userDto.getAvatar());
    userEntity.setPhone(userDto.getPhone());
    userEntity.setDob(userDto.getDob());
    userEntity.setLastName(userDto.getLastName());
    userEntity.setFirstName(userDto.getFirstName());
    userEntity.setModifiedDate(Date.from(instant));

    final UserEntity updatedUser = userRepository.save(userEntity);
    log.info("User with id {} has been updated", id);

    return modelMapper.map(updatedUser, UserDto.class);
  }

  @Override
  public UserDto disableUser(Long id) {
    UserEntity userEntity =
        userRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Not found user with id: " + id));

    Instant instant = Instant.now();

    userEntity.setStatus(BaseConstants.USER_STATUS_DISABLED);
    userEntity.setModifiedDate(Date.from(instant));

    UserEntity disabledUser = userRepository.save(userEntity);

    log.info("User with id {} has been disabled", id);

    return modelMapper.map(disabledUser, UserDto.class);
  }

  @Override
  public long totalUsers() {
    long total = userRepository.count();
    log.info("Total amount of users: {} ", total);
    return total;
  }

  @Override
  public List<UserDto> get10NewestCreatedUsers() {
    log.info("Get 10 newest created users");
    return userRepository.get10NewestUsers().stream()
        .map(user -> modelMapper.map(user, UserDto.class))
        .collect(Collectors.toList());
  }

  @Override
  public UserDto getUserByUsername(String username) {
    log.info("Get user  detail information by username ");
    UserEntity user = userRepository.findByUsername(username);

    if (user != null) {
      return modelMapper.map(user, UserDto.class);
    } else {
      throw new ResourceNotFoundException("User not found");
    }
  }

  @Override
  public List<UserDto> getUserByRole(String role) {
    log.info("Get user by role: {}", role);
    Optional<RoleEntity> roleEntity = roleRepository.findByRole(role);
    if (!roleEntity.isPresent()) throw new ResourceNotFoundException("Not exist role : " + role);

    return userRepository.getUserByRole(role).stream()
        .map(user -> modelMapper.map(user, UserDto.class))
        .collect(Collectors.toList());
  }

  private String generateUsername(String email) {
    StringBuilder username = new StringBuilder();
    username.append(email.split("@")[0].toLowerCase());
    Optional<Integer> maxPostfix = userRepository.findMaxUsernamePostfix(username.toString());
    maxPostfix.ifPresent(postfix -> username.append(++postfix));
    log.info("username: {}", username);
    return username.toString();
  }

  private String generatePassword(String username, Date dob) {
    SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
    StringBuilder password = new StringBuilder();

    password.append(username).append("@");
    password.append(formatter.format(dob));
    String encodedPassword = passwordEncoder.encode(password);

    log.info("encoded password: {}", encodedPassword);
    return encodedPassword;
  }

  @Override
  public UserDto getCurrentUser() {
    UserDetails userDetails =
        (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    UserEntity user = userRepository.findByUsername(userDetails.getUsername());

    return modelMapper.map(user, UserDto.class);
  }

  private boolean isBlank(String name) {
    return name == null || name.trim().isEmpty();
  }
}
