package com.btec.cms.service;

import com.btec.cms.dto.*;
import com.btec.cms.exception.ResourceAlreadyExistsException;
import com.btec.cms.exception.ResourceNotFoundException;

import java.util.List;

public interface CourseService {

  /**
   * Get all courses
   *
   * @return {@link List<CourseResponse>} list courses
   */
  List<CourseResponse> getAllCourse();

  /**
   * Get total number of courses
   *
   * @return total number of courses
   */
  long getTotalCourse();

  /**
   * Create new course
   *
   * @param courseDto new course detail information
   * @return {@link CourseDto} new course
   * @exception ResourceAlreadyExistsException if courses name existed
   * @exception ResourceNotFoundException course name empty
   */
  CourseDto createCourse(CourseDto courseDto);

  /**
   * Get course by id
   *
   * @param id course id
   * @return {@link CourseDetailDto} course found
   * @exception ResourceNotFoundException if not found course
   */
  CourseDetailDto getCourseById(Long id);

  /**
   * Update course detail
   *
   * @param courseDetailDto new course detail
   * @param id course id
   * @return {@link CourseResponse} updated course
   */
  CourseDetailDto updateCourse(CourseDetailDto courseDetailDto, Long id);

  /**
   * Get 2 latest courses
   *
   * @return {@link List<CourseDetailDto>} 2 latest courses
   */
  List<CourseDetailDto> get2LatestCourses();

  /**
   * Get all students in course
   *
   * @param id course id
   * @return {@link List<UserDto>} list student in course
   * @exception ResourceNotFoundException not found course
   */
  List<UserDto> getStudentsInCourse(Long id);

  /**
   * Get all assignments in course
   *
   * @param id course id
   * @return {@link List<UserDto>} list assignments in course
   * @exception ResourceNotFoundException not found course
   */
  List<AssignmentResponse> getAllAssignmentsInCourse(Long id);

  /**
   * Get all courses assigned to teacher
   *
   * @return {@link List<CourseResponse>} list courses assigned to teacher
   */
  List<CourseResponse> getAllCoursesAssignedToTeacher();

  /**
   * Get all courses of student
   *
   * @return {@link List<CourseAssignedToStudent>} list courses of student
   */
  List<CourseAssignedToStudent> getAllCoursesOfStudent();
}
