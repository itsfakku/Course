package com.btec.cms.api;

import com.btec.cms.dto.*;
import com.btec.cms.exception.ResourceAlreadyExistsException;
import com.btec.cms.exception.ResourceNotFoundException;
import com.btec.cms.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/courses")
@CrossOrigin(origins = "*")
public class CourseApi {
  @Autowired CourseService courseService;

  private static final Logger log = LoggerFactory.getLogger(CourseApi.class);

  /**
   * API to get all courses <br>
   * Link: <code>/courses/</code> <br>
   * Method: GET
   *
   * @return {@link List<CourseResponse>} list of courses
   */
  @GetMapping()
  public ResponseEntity<?> getAllCourses() {
    log.info("Get all courses");
    return ResponseEntity.ok(courseService.getAllCourse());
  }

  /**
   * API to get total number of courses <br>
   * Link: <code>/courses/total</code> <br>
   * Method: GET
   *
   * @return total number of courses
   */
  @GetMapping("/total")
  public long totalCourse() {
    log.info("Get total number of courses");
    return courseService.getTotalCourse();
  }

  /**
   * API to create new course <br>
   * Link: <code>/courses</code> <br>
   * Method: POST
   *
   * @return {@link CourseResponse} new course
   * @exception ResourceAlreadyExistsException if courses name existed
   * @exception ResourceNotFoundException course name empty
   */
  @PostMapping()
  public ResponseEntity<?> createCourse(@Valid @RequestBody CourseDto courseDto) {
    log.info("Create new course");
    return ResponseEntity.ok(courseService.createCourse(courseDto));
  }

  /**
   * API to get course by id<br>
   * Link: <code>/courses/{id}</code> <br>
   * Method: GET
   *
   * @return {@link CourseDto} course found
   */
  @GetMapping("/{id}")
  public ResponseEntity<?> getCourseById(@PathVariable Long id) {
    log.info("Get course by id");
    return ResponseEntity.ok(courseService.getCourseById(id));
  }

  /**
   * API to update course <br>
   * Link: <code>/courses/{id}</code> <br>
   * Method: PATCH
   *
   * @return {@link CourseResponse} updated course
   * @exception ResourceNotFoundException if not found course
   */
  @PatchMapping("/{id}")
  public ResponseEntity<?> updateCourse(
      @RequestBody CourseDetailDto courseDetailDto, @PathVariable Long id) {
    log.info("Update course");
    return ResponseEntity.ok(courseService.updateCourse(courseDetailDto, id));
  }

  /**
   * API to get 2 latest courses <br>
   * Link: <code>/courses/latest</code> <br>
   * Method: GET
   *
   * @return {@link List<CourseDetailDto>} 2 latest courses
   */
  @GetMapping("/latest")
  public ResponseEntity<?> get2LatestCourses() {
    log.info("Get 2 latest courses");
    return ResponseEntity.ok(courseService.get2LatestCourses());
  }

  /**
   * API to get all students in course <br>
   * Link: <code>/courses/{id}/students</code> <br>
   * Method: GET
   *
   * @return {@link List<UserDto>} list students in course
   * @exception ResourceNotFoundException not found course
   */
  @GetMapping("/{id}/students")
  public ResponseEntity<?> getStudentsInCourse(@PathVariable Long id) {
    log.info("Get all students in course");
    return ResponseEntity.ok(courseService.getStudentsInCourse(id));
  }

  /**
   * API to get all assignments in course <br>
   * Link: <code>/courses/{id}/assignments</code> <br>
   * Method: GET
   *
   * @return {@link List<AssignmentResponse>} list assignments in course
   * @exception ResourceNotFoundException not found course
   */
  @GetMapping("/{id}/assignments")
  public ResponseEntity<?> getAllAssignment(@PathVariable Long id) {
    log.info("Get all assignments in course");
    return ResponseEntity.ok(courseService.getAllAssignmentsInCourse(id));
  }
}
