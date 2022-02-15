package com.btec.cms.service.impl;

import com.btec.cms.dto.*;
import com.btec.cms.entity.AsmSubmissionEntity;
import com.btec.cms.entity.AssignmentEntity;
import com.btec.cms.entity.CourseEntity;
import com.btec.cms.entity.UserEntity;
import com.btec.cms.exception.ResourceNotFoundException;
import com.btec.cms.repository.AssignmentRepository;
import com.btec.cms.repository.SubmissionRepository;
import com.btec.cms.repository.UserRepository;
import com.btec.cms.service.FilesStorageService;
import com.btec.cms.service.SubmissionService;
import com.btec.cms.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubmissionServiceImpl implements SubmissionService {

  private final SubmissionRepository submissionRepository;
  private final UserRepository userRepository;
  private final FilesStorageService storageService;
  private final UserService userService;
  private final AssignmentRepository assignmentRepository;
  private final ModelMapper modelMapper;
  private static final Logger log = LoggerFactory.getLogger(SubmissionServiceImpl.class);

  @Autowired
  public SubmissionServiceImpl(
      SubmissionRepository submissionRepository,
      UserRepository userRepository,
      FilesStorageService storageService,
      UserService userService,
      AssignmentRepository assignmentRepository,
      ModelMapper modelMapper) {
    this.submissionRepository = submissionRepository;
    this.userRepository = userRepository;
    this.storageService = storageService;
    this.userService = userService;
    this.assignmentRepository = assignmentRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  @Transactional
  public void createSubmission(Long studentId, Long asmId) {
    log.info("Create assignment submission");

    if (!assignmentRepository.findById(asmId).isPresent())
      throw new ResourceNotFoundException("Not found assignment with id " + asmId);
    if (!userRepository.findById(studentId).isPresent())
      throw new ResourceNotFoundException("Not found user with id " + studentId);

    AsmSubmissionDto asmSubmissionDto = new AsmSubmissionDto();
    asmSubmissionDto.setAsmId(asmId);
    asmSubmissionDto.setUserId(studentId);
    asmSubmissionDto.setSubmitStatus(0);

    AsmSubmissionEntity asmSubmission =
        modelMapper.map(asmSubmissionDto, AsmSubmissionEntity.class);

    asmSubmission.setUser(userRepository.findById(asmSubmissionDto.getUserId()).get());
    asmSubmission.setAssignment(assignmentRepository.findById(asmSubmissionDto.getAsmId()).get());

    log.info("Asm submission {}", asmSubmission);
    AsmSubmissionEntity createdAsmSubmission = submissionRepository.save(asmSubmission);

    log.info("Created submission {}", createdAsmSubmission);

    modelMapper.map(createdAsmSubmission, AsmSubmissionDto.class);
  }

  @Override
  public List<SubmissionResponse> getAllSubmissionInAssignment(Long id) {
    log.info("Get all submission in assignment");

    Optional<AssignmentEntity> assignment = assignmentRepository.findById(id);
    if (!assignment.isPresent()) throw new ResourceNotFoundException("Not found assignment");

    // map to submission response and return
    return submissionRepository.getAllSubmissions(id).stream()
        .map(
            submission -> {
              SubmissionResponse submissionResponse = new SubmissionResponse();
              modelMapper.map(submission, submissionResponse);
              int submitIntValue = Integer.parseInt(submissionResponse.getSubmitStatus());
              submissionResponse.setSubmitStatus(genSubmitStatus(submitIntValue));
              return submissionResponse;
            })
        .collect(Collectors.toList());
  }

  @Override
  public SubmissionDetailDto updateSubmission(MultipartFile file, Long asmId) {

    log.info("Update submission");

    if (!assignmentRepository.findById(asmId).isPresent())
      throw new ResourceNotFoundException("Not found assignment with id " + asmId);

    UserDto userDto = userService.getCurrentUser();
    UserEntity currentUser = userRepository.getById(userDto.getId());

    AssignmentEntity assignment = assignmentRepository.getById(asmId);
    CourseEntity course = assignment.getCourseEntityAssigment();

    String fileName = generateFileName(course.getId(), assignment.getId(), userDto.getId());

    String fileUrl = storageService.save(file, fileName);

    // get submission by user and assignment
    AsmSubmissionEntity asmSubmission =
        submissionRepository
            .findAsmSubmissionEntityByAssignmentAndUser(assignment, currentUser)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Not found submission of user "
                            + currentUser.getFirstName()
                            + " "
                            + currentUser.getLastName()
                            + "  in this assignment"));

    asmSubmission.setUser(currentUser);
    asmSubmission.setAssignment(assignment);
    asmSubmission.setFileUrl(fileUrl);
    asmSubmission.setFileName(file.getOriginalFilename());
    asmSubmission.setSubmitDate(Date.from(Instant.now()));
    asmSubmission.setSubmitStatus(1);

    AsmSubmissionEntity updatedSubmission = submissionRepository.save(asmSubmission);
    return modelMapper.map(updatedSubmission, SubmissionDetailDto.class);
  }

  @Override
  public SubmissionDetailDto gradeSubmission(Long asmId, Long userId, GradeDetail gradeDetail) {
    log.info("Grade submission");

    if (!assignmentRepository.findById(asmId).isPresent())
      throw new ResourceNotFoundException("Not found assignment with id " + asmId);

    if (!userRepository.findById(userId).isPresent())
      throw new ResourceNotFoundException("Not found user with id " + userId);

    UserEntity user = userRepository.getById(userId);
    AssignmentEntity assignment = assignmentRepository.getById(asmId);

    // get submission by user and assignment
    AsmSubmissionEntity asmSubmission =
        submissionRepository
            .findAsmSubmissionEntityByAssignmentAndUser(assignment, user)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Not found submission of user "
                            + user.getFirstName()
                            + " "
                            + user.getLastName()
                            + "  in this assignment"));

    asmSubmission.setGrade(gradeDetail.getGrade());
    asmSubmission.setComment(gradeDetail.getComment());
    asmSubmission.setGradeStatus(1);
    asmSubmission.setGradeDate(Date.from(Instant.now()));

    AsmSubmissionEntity gradedSubmission = submissionRepository.save(asmSubmission);
    return modelMapper.map(gradedSubmission, SubmissionDetailDto.class);
  }

  @Override
  public StudentSubmissionDetail getSubmissionContent(Long asmId, Long studentId) {
    log.info("Get submission content");

    Object submissionObject = submissionRepository.getSubmissionContent(asmId, studentId);
    StudentSubmissionDetail submissionContent = new StudentSubmissionDetail();
    modelMapper.map(submissionObject, submissionContent);

    return submissionContent;
  }

  private String genSubmitStatus(int status) {
    if (status == 1) return "In Time";
    if (status == 0) return "Not submit";
    return "Late";
  }

  private String generateFileName(Long courseId, Long asmId, Long userId) {
    return "" + courseId + asmId + userId;
  }
}
