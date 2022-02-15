package com.btec.cms.service;

import com.btec.cms.dto.AccountDto;
import com.btec.cms.dto.UserDto;
import com.btec.cms.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserService {

  /**
   * Get user information by username
   *
   * @param username the username
   * @return {@link Optional<AccountDto>}
   */
  Optional<AccountDto> findByUsername(String username);

  /**
   * Get all users
   *
   * @return {@link List<UserDto>}
   */
  List<UserDto> getAllUsers();

  /**
   * Get user detail information by id
   *
   * @param id user id
   * @return {@link UserDto}
   */
  UserDto getUserById(Long id);

  /**
   * Create a user
   *
   * @param userDto new user
   * @return {@link UserDto} created user
   */
  UserDto createUser(UserDto userDto);

  /**
   * Delete user by id
   *
   * @param id user id
   */
  void deleteUser(Long id);

  /**
   * Update a user detail information
   *
   * @param id user id
   * @param userDto new user information
   * @return {@link UserDto} updated user
   */
  UserDto updateUser(Long id, UserDto userDto);

  /**
   * Disable a user
   *
   * @param id user id
   * @return {@link UserDto} disabled user
   */
  UserDto disableUser(Long id);

  /**
   * Get total amount of users
   *
   * @return {@link Integer} total amount of users
   */
  long totalUsers();

  /**
   * Get 10 newest created user
   *
   * @return {@link List<UserDto>} List 10 newest created users
   */
  List<UserDto> get10NewestCreatedUsers();

  /**
   * Get user by username
   *
   * @param username username of user
   * @return {@link UserDto} user found
   * @exception ResourceNotFoundException if user not found
   */
  UserDto getUserByUsername(String username);

  /**
   * Get users by role id
   *
   * @param role role of users
   * @return {@link List<UserDto>} list users found
   * @exception ResourceNotFoundException if role not existed
   */
  List<UserDto> getUserByRole(String role);

  /**
   * Get current logged user
   *
   * @return {@link UserDto} current logged user
   */
  UserDto getCurrentUser();
}
