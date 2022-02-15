package com.btec.cms.service.impl;

import com.btec.cms.common.BaseConstants;
import com.btec.cms.dto.*;
import com.btec.cms.entity.CategoryEntity;
import com.btec.cms.entity.CourseEntity;
import com.btec.cms.entity.TopicEntity;
import com.btec.cms.entity.UserEntity;
import com.btec.cms.exception.ResourceAlreadyExistsException;
import com.btec.cms.exception.ResourceEditException;
import com.btec.cms.exception.ResourceNotFoundException;
import com.btec.cms.repository.*;
import com.btec.cms.service.CourseService;
import com.btec.cms.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

  private final CourseRepository courseRepository;
  private final UserRepository userRepository;
  private final TopicRepository topicRepository;
  private final CategoryRepository categoryRepository;
  private final UserService userService;
  private final AssignmentRepository assignmentRepository;
  private final ModelMapper modelMapper;
  private static final Logger log = LoggerFactory.getLogger(CourseServiceImpl.class);

  @Autowired
  public CourseServiceImpl(
      CourseRepository courseRepository,
      UserRepository userRepository,
      TopicRepository topicRepository,
      CategoryRepository categoryRepository,
      UserService userService,
      AssignmentRepository assignmentRepository,
      ModelMapper modelMapper) {
    this.courseRepository = courseRepository;
    this.userRepository = userRepository;
    this.topicRepository = topicRepository;
    this.categoryRepository = categoryRepository;
    this.userService = userService;
    this.assignmentRepository = assignmentRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public List<CourseResponse> getAllCourse() {
    log.info("Get all courses");

    return courseRepository.getAllCourses().stream()
        .map(course -> modelMapper.map(course, CourseResponse.class))
        .sorted((Comparator.comparing(CourseResponse::getId)))
        .collect(Collectors.toList());
  }

  @Override
  public long getTotalCourse() {
    long total = courseRepository.count();
    log.info("Total number of courses: {}", total);
    return total;
  }

  @Override
  public CourseDto createCourse(CourseDto courseDto) {
    log.info("Create new course");

    // check blank name
    if (isBlankName(courseDto.getName()))
      throw new ResourceNotFoundException("Course name can not be empty");

    // check exist course
    if (courseRepository.existsByName(courseDto.getName()))
      throw new ResourceAlreadyExistsException("Course name", courseDto.getName());

    // get users
    Optional<UserEntity> teacher = userRepository.findById(courseDto.getTeacherId());

    if (!teacher.isPresent()) throw new ResourceNotFoundException("Teacher not found");

    // get topic and category by name
    Optional<TopicEntity> topic = topicRepository.findById(courseDto.getTopicId());
    Optional<CategoryEntity> category = categoryRepository.findById(courseDto.getCategoryId());

    CourseEntity course = modelMapper.map(courseDto, CourseEntity.class);

    category.ifPresent(course::setCategoryEntity);
    topic.ifPresent(course::setTopicEntity);
    course.setStatus(generateStatus(courseDto.getStartDate(), courseDto.getEndDate()));
    teacher.ifPresent(course::setTeacher);

    CourseEntity createdCourse = courseRepository.save(course);

    createdCourse = courseRepository.save(createdCourse);
    log.info("Created Course {}", createdCourse);

    // response updated course detail
    return modelMapper.map(createdCourse, CourseDto.class);
  }

  public CourseDetailDto getCourseById(Long id) {
    log.info("Get course by id {}", id);
    return courseRepository
        .findById(id)
        .map(
            course -> {
              CourseDetailDto courseDetailDto = modelMapper.map(course, CourseDetailDto.class);
              courseDetailDto.setStudentIds(
                  course.getStudents().stream().map(UserEntity::getId).collect(Collectors.toSet()));
              return courseDetailDto;
            })
        .orElseThrow(() -> new ResourceNotFoundException("Not found course with id " + id));
  }

  @Override
  public CourseDetailDto updateCourse(CourseDetailDto courseDetailDto, Long id) {
    log.info("Update topic");
    Instant instant = Instant.now();
    // check exist course
    CourseEntity course =
        courseRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Not found course with id: " + id));

    if (!course.getStatus().equalsIgnoreCase(BaseConstants.COURSE_NEW)) {
      throw new ResourceEditException("Can not edit course already teaching or done");
    }

    if (courseDetailDto.getTeacherId() == null)
      throw new IllegalArgumentException("Teacher id can not null");

    // get teacher by name and role
    Optional<UserEntity> teacher =
        userRepository.findByIdAndRoleId(
            courseDetailDto.getTeacherId(), BaseConstants.ROLE_ID_TEACHER);

    // get student by id return set
    Set<UserEntity> students =
        courseDetailDto.getStudentIds().stream()
            .map(
                studentId ->
                    userRepository
                        .findByIdAndRoleId(studentId, BaseConstants.ROLE_ID_STUDENT)
                        .orElseThrow(
                            () ->
                                new ResourceNotFoundException(
                                    "Not found student with id: " + studentId)))
            .collect(Collectors.toSet());

    Optional<TopicEntity> topic = topicRepository.findById(courseDetailDto.getTopicId());
    Optional<CategoryEntity> category =
        categoryRepository.findById(courseDetailDto.getCategoryId());

    // update course information
    category.ifPresent(course::setCategoryEntity);
    topic.ifPresent(course::setTopicEntity);
    course.setEnrollCode(courseDetailDto.getEnrollCode());
    course.setStartDate(courseDetailDto.getStartDate());
    course.setEndDate(courseDetailDto.getEndDate());
    teacher.ifPresent(course::setTeacher);
    course.setStudents(students);
    course.setStatus(generateStatus(course.getStartDate(), course.getEndDate()));
    course.setModifiedDate(Date.from(instant));
    course.setDescription(courseDetailDto.getDescription());

    // save course information
    final CourseEntity updatedCourse = courseRepository.save(course);

    log.info("Course with id {} has been updated", id);

    // response updated course detail
    CourseDetailDto courseResponse = modelMapper.map(updatedCourse, CourseDetailDto.class);
    courseResponse.setStudentIds(
        course.getStudents().stream().map(UserEntity::getId).collect(Collectors.toSet()));
    return courseResponse;
  }

  @Override
  public List<CourseDetailDto> get2LatestCourses() {
    log.info("Get 2 latest courses");
    return courseRepository.findTop2ByOrderByCreatedDateDesc().stream()
        .map(
            course -> {
              CourseDetailDto courseDetailDto = modelMapper.map(course, CourseDetailDto.class);
              courseDetailDto.setStudentIds(
                  course.getStudents().stream().map(UserEntity::getId).collect(Collectors.toSet()));
              return courseDetailDto;
            })
        .collect(Collectors.toList());
  }

  @Override
  public List<UserDto> getStudentsInCourse(Long id) {
    log.info("Get students in course");
    if (!courseRepository.findById(id).isPresent())
      throw new ResourceNotFoundException("Not found course with id: " + id);

    List<UserDto> studentList =
        userRepository.getStudentInCourse(id).stream()
            .map(userEntity -> modelMapper.map(userEntity, UserDto.class))
            .collect(Collectors.toList());
    return studentList;
  }

  @Override
  public List<AssignmentResponse> getAllAssignmentsInCourse(Long id) {
    log.info("Get all assignments in course");

    if (!courseRepository.findById(id).isPresent())
      throw new ResourceNotFoundException("Not found course with id: " + id);

    List<AssignmentResponse> assignmentList =
        assignmentRepository.getALlAssignmentsInCourse(id).stream()
            .map(asm -> modelMapper.map(asm, AssignmentResponse.class))
            .collect(Collectors.toList());
    return assignmentList;
  }

  @Override
  public List<CourseResponse> getAllCoursesAssignedToTeacher() {
    log.info("Get courses assignment to teacher");

    UserDto currentTeacher = userService.getCurrentUser();
    return courseRepository.getAllCoursesAssignedToTeacher(currentTeacher.getId()).stream()
        .map(course -> modelMapper.map(course, CourseResponse.class)).collect(Collectors.toList());
}

  @Override
  public List<CourseAssignedToStudent> getAllCoursesOfStudent() {
    log.info("Get all courses of a student");

    UserDto currentUser = userService.getCurrentUser();

    List<Object> courseList = courseRepository.getAllCoursesOfStudent(currentUser.getId());
    return courseList.stream()
        .map(
            course -> {
              {
                CourseAssignedToStudent courseAssignedToStudent = new CourseAssignedToStudent();
                modelMapper.map(course, courseAssignedToStudent);
                courseAssignedToStudent.setStatus(
                    generateGradeStatus(courseAssignedToStudent.getGrade()));
                return courseAssignedToStudent;
              }
            })
        .collect(Collectors.toList());
  }

  private String generateStatus(Date startDate, Date endDate) {
    Instant instant = Instant.now();
    if (startDate.after(Date.from(instant))) {
      return BaseConstants.COURSE_NEW;
    } else if (endDate.before(Date.from(instant))) {
      return BaseConstants.COURSE_DONE;
    } else return BaseConstants.COURSE_TEACHING;
  }

  private boolean isBlankName(String name) {
    return name == null || name.trim().isEmpty();
  }

  private String generateGradeStatus(float grade) {
    if (grade >= 80) {
      return "Distinction";
    } else if (grade >= 60) {
      return "Merit";
    } else if (grade >= 40) {
      return "Pass";
    }
    return "Fail";
  }
}
