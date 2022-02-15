package com.btec.cms.api;

import com.btec.cms.dto.CourseAssignedToStudent;
import com.btec.cms.dto.CourseResponse;
import com.btec.cms.dto.UserDto;
import com.btec.cms.exception.ResourceNotFoundException;
import com.btec.cms.service.CourseService;
import com.btec.cms.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** API for User */
@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserApi {

  @Autowired UserService userService;
  @Autowired CourseService courseService;
  private static final Logger log = LoggerFactory.getLogger(UserApi.class);

  /**
   * API Get all users <br>
   * Link: <code>/users</code> <br>
   * Method: GET
   *
   * @return {@link List<UserDto>}
   */
  @GetMapping()
  public ResponseEntity<?> getAllUsers() {
    log.info("Get all users");
    return ResponseEntity.ok(userService.getAllUsers());
  }

  /**
   * API Get user detail information <br>
   * Link: <code>/users/{id}</code> <br>
   * Method: GET
   *
   * @return {@link UserDto}
   */
  @GetMapping("/{id}")
  public ResponseEntity<?> getUserById(@PathVariable(name = "id") Long id) {
    log.info("Get detail information of user {}", id);
    return ResponseEntity.ok(userService.getUserById(id));
  }

  /**
   * API Create user <br>
   * Link: <code>/users</code> <br>
   * Method: POST
   *
   * @return {@link UserDto}
   */
  @PostMapping()
  public ResponseEntity<?> createUser(@RequestBody UserDto userDto) {
    log.info("Create new user with email: {}", userDto.getEmail());
    return ResponseEntity.ok(userService.createUser(userDto));
  }

  /**
   * API Delete user by id <br>
   * Link: <code>/users/{id}</code> <br>
   * Method: DELETE
   *
   * @param id user id
   */
  @DeleteMapping("/{id}")
  public void deleteUser(@PathVariable(name = "id") Long id) {
    userService.deleteUser(id);
    log.info("User with id {} has been deleted", id);
  }

  /**
   * API Update user detail information <br>
   * Link: <code>/users/{id}</code> <br>
   * Method: PUT
   *
   * @param id user id need to update
   * @param userDto new user detail information
   */
  @PutMapping("/{id}")
  public ResponseEntity<?> updateUser(
      @PathVariable(name = "id") Long id, @RequestBody UserDto userDto) {
    log.info("Update  user with id: {}", id);
    return ResponseEntity.ok(userService.updateUser(id, userDto));
  }

  /**
   * API Disable user by id <br>
   * Link: <code>/users/{id}</code> <br>
   * Method: PATCH
   *
   * @param id user id
   * @return {@link UserDto} disabled user
   */
  @PatchMapping("/{id}")
  public ResponseEntity<?> disableUser(@PathVariable(name = "id") Long id) {
    log.info("Disable user with id: {}", id);
    return new ResponseEntity<>(userService.disableUser(id), HttpStatus.OK);
  }

  /**
   * API Get total amount of users <br>
   * Link: <code>/users/total</code> <br>
   * Method: GET
   *
   * @return long value total mount of users
   */
  @GetMapping("/total")
  public long totalUsers() {
    return userService.totalUsers();
  }

  /**
   * API Get 10 newest created users<br>
   * Link: <code>/users/10/latest</code> <br>
   * Method: GET
   *
   * @return {@link ResponseEntity} 10 latest users
   */
  @GetMapping("/10/latest")
  public ResponseEntity<?> get10NewestCreatedUsers() {
    return ResponseEntity.ok(userService.get10NewestCreatedUsers());
  }

  /**
   * API Get user by username<br>
   * Link: <code>/users/?username={username}</code> <br>
   * Method: GET
   *
   * @return {@link ResponseEntity} user found
   * @exception ResourceNotFoundException if user not found
   */
  @GetMapping("/")
  public ResponseEntity<?> getUserByUsername(@RequestParam String username) {
    return ResponseEntity.ok(userService.getUserByUsername(username));
  }

  /**
   * API Get user by role <br>
   * Link: <code>/users/role/{role}</code> <br>
   * Method: GET
   *
   * @return {@link List<UserDto>} list users found
   */
  @GetMapping("/role/{role}")
  public ResponseEntity<?> getUserByRole(@PathVariable(name = "role") String role) {
    log.info("Get user by role");
    return ResponseEntity.ok(userService.getUserByRole(role));
  }

  /**
   * API Get courses assigned to teacher <br>
   * Link: <code>/users/teacher/courses</code> <br>
   * Method: GET
   *
   * @return {@link List<CourseResponse>} list courses assigned to teacher
   */
  @GetMapping("/teacher/courses")
  public ResponseEntity<?> getAllCourseAssignedToTeacher() {
    log.info("Get courses assigned to teacher");
    return ResponseEntity.ok(courseService.getAllCoursesAssignedToTeacher());
  }

  /**
   * API Get courses of student <br>
   * Link: <code>/users/student/courses</code> <br>
   * Method: GET
   *
   * @return {@link List<CourseAssignedToStudent>} list courses found
   */
  @GetMapping("/student/courses")
  public ResponseEntity<?> getAllCoursesOfStudent() {
    return ResponseEntity.ok(courseService.getAllCoursesOfStudent());
  }
}
