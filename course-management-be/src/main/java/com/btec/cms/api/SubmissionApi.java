package com.btec.cms.api;

import com.btec.cms.dto.GradeDetail;
import com.btec.cms.dto.SubmissionDetailDto;
import com.btec.cms.service.SubmissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/submissions")
@CrossOrigin(origins = "*")
public class SubmissionApi {
  @Autowired SubmissionService submissionService;
  private static final Logger log = LoggerFactory.getLogger(SubmissionApi.class);

  /**
   * Grade submission <br>
   * Link: <code>/submissions/{asmId}/grade/{userId}</code> <br>
   * Method: PATCH
   *
   * @param asmId assignment id
   * @param userId user id
   * @param gradeDetail grade detail for submission
   * @return {@link SubmissionDetailDto} graded assignment
   */
  @PatchMapping("/{asmId}/grade/{userId}")
  public ResponseEntity<?> gradeSubmission(
      @PathVariable("asmId") Long asmId,
      @PathVariable("userId") Long userId,
      @RequestBody GradeDetail gradeDetail) {

    log.info("grade submission");
    return ResponseEntity.ok(submissionService.gradeSubmission(asmId, userId, gradeDetail));
  }
}
