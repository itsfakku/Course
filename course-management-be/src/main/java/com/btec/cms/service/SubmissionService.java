package com.btec.cms.service;

import com.btec.cms.dto.GradeDetail;
import com.btec.cms.dto.StudentSubmissionDetail;
import com.btec.cms.dto.SubmissionDetailDto;
import com.btec.cms.dto.SubmissionResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SubmissionService {

  /** Add new submission to assignment */
  void createSubmission(Long studentId, Long asmId);

  /**
   * Get all submissions
   *
   * @param id assignment id
   * @return {@link List<SubmissionResponse>} all submission in assignment
   */
  List<SubmissionResponse> getAllSubmissionInAssignment(Long id);

  SubmissionDetailDto updateSubmission(MultipartFile file, Long asmId);

  /**
   * Grade submission
   *
   * @param asmId assignment id
   * @param userId user id
   * @param gradeDetail detail grade for submission
   * @return {@link SubmissionDetailDto} graded submission
   */
  SubmissionDetailDto gradeSubmission(Long asmId, Long userId, GradeDetail gradeDetail);

  /**
   * Get submission content
   *
   * @param asmId assignment id
   * @param studentId student id
   * @return {@link SubmissionDetailDto} found submission
   */
  StudentSubmissionDetail getSubmissionContent(Long asmId, Long studentId);
}
