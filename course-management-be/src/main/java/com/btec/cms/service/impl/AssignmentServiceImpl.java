package com.btec.cms.service.impl;

import com.btec.cms.common.BaseConstants;
import com.btec.cms.dto.*;
import com.btec.cms.entity.AssignmentEntity;
import com.btec.cms.entity.CourseEntity;
import com.btec.cms.exception.ResourceAlreadyExistsException;
import com.btec.cms.exception.ResourceCreateException;
import com.btec.cms.exception.ResourceNotFoundException;
import com.btec.cms.repository.AssignmentRepository;
import com.btec.cms.repository.CourseRepository;
import com.btec.cms.repository.SubmissionRepository;
import com.btec.cms.service.AssignmentService;
import com.btec.cms.service.CourseService;
import com.btec.cms.service.SubmissionService;
import com.btec.cms.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AssignmentServiceImpl implements AssignmentService {

  private final AssignmentRepository assignmentRepository;
  private final SubmissionRepository submissionRepository;
  private final CourseRepository courseRepository;
  private final UserService userService;
  private final CourseService courseService;
  private final SubmissionService submissionService;
  private final ModelMapper modelMapper;

  private static final Logger log = LoggerFactory.getLogger(AssignmentServiceImpl.class);

  @Autowired
  public AssignmentServiceImpl(
      AssignmentRepository assignmentRepository,
      SubmissionRepository submissionRepository,
      CourseRepository courseRepository,
      UserService userService,
      CourseService courseService,
      SubmissionService submissionService,
      ModelMapper modelMapper) {
    this.assignmentRepository = assignmentRepository;
    this.submissionRepository = submissionRepository;
    this.courseRepository = courseRepository;
    this.userService = userService;
    this.courseService = courseService;
    this.submissionService = submissionService;
    this.modelMapper = modelMapper;
  }

  @Override
  public Optional<SubmissionDetailDto> getAssignmentById(Long asmId) {
    log.info("Get assignment detail");

    UserDto currentUser = userService.getCurrentUser();
    if (!assignmentRepository.findById(asmId).isPresent())
      throw new ResourceNotFoundException("Not found assignment with id " + asmId);

    Optional<Object> submissionObject =
        submissionRepository.getSubmissionDetail(currentUser.getId(), asmId);

    if (submissionObject.isPresent()) {
      return submissionObject.map(
          object -> {
            SubmissionDetailDto submissionDetailDto =
                modelMapper.map(object, SubmissionDetailDto.class);
            submissionDetailDto.setTimeRemaining(
                getTimeRemaining(
                    submissionDetailDto.getDueDate(), submissionDetailDto.getSubmitDate()));
            return submissionDetailDto;
          });
    } else {
      throw new ResourceNotFoundException("Not found submission");
    }
  }

  @Override
  @Transactional
  public AssignmentResponse createAssignment(AssignmentDto assignmentDto) {
    log.info("Create new assignment");

    Optional<CourseEntity> course = courseRepository.findById(assignmentDto.getCourseId());

    if (course.isPresent()) {
      if (!course.get().getStatus().equalsIgnoreCase(BaseConstants.COURSE_TEACHING))
        throw new ResourceCreateException("Can not create assignment for a new or done course");
    } else {
      throw new ResourceNotFoundException("Not found course to create assignment");
    }

    if (isBlank(assignmentDto.getAssignmentName()))
      throw new IllegalArgumentException("Assignment name cannot be empty");

    // check duplicate name
    if (assignmentRepository.countAssignmentName(
            assignmentDto.getAssignmentName(), assignmentDto.getCourseId())
        > 0)
      throw new ResourceAlreadyExistsException(
          "Assignment name", assignmentDto.getAssignmentName());

    AssignmentEntity assignment = modelMapper.map(assignmentDto, AssignmentEntity.class);

    assignment.setCourseEntityAssigment(courseRepository.getById(assignmentDto.getCourseId()));

    AssignmentEntity createdAssignment = assignmentRepository.save(assignment);
    log.info("Created assignment {}", createdAssignment);

    createBaseSubmissionForAssignment(assignmentDto.getCourseId(), createdAssignment.getId());

    return modelMapper.map(createdAssignment, AssignmentResponse.class);
  }

  @Override
  public AssignmentResponse updateAssignment(AssignmentDto assignmentDto, Long id) {
    log.info("Update assignment");

    AssignmentEntity assignment =
        assignmentRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Not found assignment id"));

    assignment.setDueDate(assignmentDto.getDueDate());
    assignment.setFeedbackContent(assignmentDto.getFeedbackContent());
    assignment.setFileFormat(assignmentDto.getFileFormat());
    assignment.setFileQuantity(assignmentDto.getFileQuantity());
    assignment.setModifiedDate(Date.from(Instant.now()));

    final AssignmentEntity updatedAssignment = assignmentRepository.save(assignment);

    log.info("Assignment with id {} has been updated", id);

    return modelMapper.map(updatedAssignment, AssignmentResponse.class);
  }

  @Override
  public AssignmentDto getAssignmentDetail(Long id) {
    log.info("Get assignment detail with id {}", id);
    return assignmentRepository
        .findById(id)
        .map(asm -> modelMapper.map(asm, AssignmentDto.class))
        .orElseThrow(() -> new ResourceNotFoundException("Not found assignment with id: " + id));
  }

  private boolean isBlank(String str) {
    return str == null || str.trim().isEmpty();
  }

  private String getTimeRemaining(Date dueDate, Date submitDate) {
    if (submitDate == null) {
      if (Instant.now().isBefore(dueDate.toInstant())) {
        long diff = dueDate.getTime() - Date.from(Instant.now()).getTime();
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);
        return diffDays + " days " + diffHours + " hours";
      } else {
        long diff = Date.from(Instant.now()).getTime() - dueDate.getTime();
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);
        return "Assignment is overdue by: " + diffDays + " days " + diffHours + " hours";
      }
    }
    if (submitDate.before(dueDate)) {
      long diff = dueDate.getTime() - submitDate.getTime();
      long diffHours = diff / (60 * 60 * 1000) % 24;
      long diffDays = diff / (24 * 60 * 60 * 1000);
      return "Assignment was submitted " + diffDays + " days " + diffHours + " hours early";
    }

    long diff = submitDate.getTime() - dueDate.getTime();
    long diffHours = diff / (60 * 60 * 1000) % 24;
    long diffDays = diff / (24 * 60 * 60 * 1000);
    return "Assignment was submitted " + diffDays + " days " + diffHours + " hours late";
  }

  private void createBaseSubmissionForAssignment(Long courseId, Long asmId) {
    // Create base submission for all students in course
    Set<Long> studentIds =
        courseService.getStudentsInCourse(courseId).stream()
            .map(BaseDto::getId)
            .collect(Collectors.toSet());
    if (studentIds.size() > 0) {
      log.info("Start create submission for students {}", studentIds);
      studentIds.forEach(id -> submissionService.createSubmission(id, asmId));
      log.info("Finish create submission for students");
    } else throw new ResourceCreateException("Student id is empty");
  }
}
