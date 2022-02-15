package com.btec.cms.service;

import com.btec.cms.dto.AssignmentDto;
import com.btec.cms.dto.AssignmentResponse;
import com.btec.cms.dto.SubmissionDetailDto;
import com.btec.cms.exception.ResourceNotFoundException;

import java.util.Optional;

public interface AssignmentService {

  /**
   * Get assignment by user id and asm id
   *
   * @param asmId assignment id
   * @return {@link SubmissionDetailDto}
   * @exception ResourceNotFoundException not found assignment
   */
  Optional<SubmissionDetailDto> getAssignmentById(Long asmId);

  /**
   * Create new assignment
   *
   * @param assignmentDto new assignment detail
   * @return {@link AssignmentResponse} created assignment
   */
  AssignmentResponse createAssignment(AssignmentDto assignmentDto);

  /**
   * Update assignment
   *
   * @param assignmentDto new assignment detail
   * @param id assignment id
   * @return {@link AssignmentResponse} updated assignment
   * @exception ResourceNotFoundException not found assignment
   */
  AssignmentResponse updateAssignment(AssignmentDto assignmentDto, Long id);

  /**
   * Get assignment detail by id
   *
   * @param id assignment id
   * @return {@link AssignmentDto} assignment detail
   * @exception ResourceNotFoundException not found assignment
   */
  AssignmentDto getAssignmentDetail(Long id);
}
