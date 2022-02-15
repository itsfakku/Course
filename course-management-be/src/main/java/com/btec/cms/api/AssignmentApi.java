package com.btec.cms.api;

import com.btec.cms.dto.AssignmentDto;
import com.btec.cms.dto.AssignmentResponse;
import com.btec.cms.dto.SubmissionDetailDto;
import com.btec.cms.dto.SubmissionResponse;
import com.btec.cms.exception.ResourceNotFoundException;
import com.btec.cms.service.AssignmentService;
import com.btec.cms.service.SubmissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/assignments")
@CrossOrigin(origins = "*")
public class AssignmentApi {

  @Autowired AssignmentService assignmentService;
  @Autowired SubmissionService submissionService;
  private static final Logger log = LoggerFactory.getLogger(AssignmentApi.class);

  /**
   * Get assignment by id <br>
   * Link: <code>/assignments/{id}</code> <br>
   * Method: GET
   *
   * @param asmId assignment id
   * @return {@link AssignmentResponse} assignment found detail information
   * @exception ResourceNotFoundException if not found assignment
   */
  @GetMapping("/{asmId}")
  public ResponseEntity<?> getAssignmentById(@PathVariable Long asmId) {
    log.info("Get assignment by id");
    return ResponseEntity.ok(assignmentService.getAssignmentById(asmId));
  }

  /**
   * Create new assignment <br>
   * Link: <code>/assignments</code> <br>
   * Method: POST
   *
   * @param assignmentDto new assignment detail
   * @return {@link AssignmentResponse} created assignment
   */
  @PostMapping()
  public ResponseEntity<?> createAssignment(@RequestBody AssignmentDto assignmentDto) {
    log.info("Create new assignment");
    return ResponseEntity.ok(assignmentService.createAssignment(assignmentDto));
  }

  /**
   * Update assignment <br>
   * Link: <code>/assignments/{id}</code> <br>
   * Method: PATCH
   *
   * @param assignmentDto new assignment detail
   * @param id assignment id
   * @return {@link AssignmentResponse} updated assignment
   * @exception ResourceNotFoundException not found assignment
   */
  @PatchMapping("/{id}")
  ResponseEntity<?> updateAssignemnt(
      @RequestBody AssignmentDto assignmentDto, @PathVariable Long id) {
    log.info("Update assignment");
    return ResponseEntity.ok(assignmentService.updateAssignment(assignmentDto, id));
  }

  /**
   * API to get all submissions in assignment<br>
   * Link: <code>/assignments/{id}/submissions</code> <br>
   *
   * @param id assignment id
   * @return {@link List<SubmissionResponse>} list submissions in assignment
   */
  @GetMapping("/{id}/submissions")
  public ResponseEntity<?> getAllSubmissions(@PathVariable Long id) {
    log.info("Get all submission");
    return ResponseEntity.ok(submissionService.getAllSubmissionInAssignment(id));
  }

  /**
   * Get assignment detail by id <br>
   * Link: <code>/assignments?id={id}</code> <br>
   * Method: GET
   *
   * @param id assignment id
   * @return {@link AssignmentDto} assignment found detail information
   * @exception ResourceNotFoundException if not found assignment
   */
  @GetMapping("")
  public ResponseEntity<?> getAssignmentDetail(@RequestParam Long id) {
    log.info("Get assignment detail by id");
    return ResponseEntity.ok(assignmentService.getAssignmentDetail(id));
  }

  /**
   * Submit assignment<br>
   * Link: <code>/assignments/{id}/submit</code> <br>
   * Method: PATCH
   *
   * @param id assignment id
   * @param file file submit
   * @return {@link SubmissionDetailDto} updated assignment
   */
  @PatchMapping("/{id}/submit")
  public ResponseEntity<?> editSubmission(
      @RequestParam("file") MultipartFile file, @PathVariable("id") Long id) {
    log.info("update submission");
    return ResponseEntity.ok(submissionService.updateSubmission(file, id));
  }

  /**
   * Get submission content<br>
   * Link: <code>/assignments/{asmId}/student/{studentId}</code> <br>
   * Method: PATCH
   *
   * @param asmId assignment id
   * @param studentId student id
   * @return {@link SubmissionDetailDto} updated assignment
   */
  @GetMapping("/{asmId}/student/{studentId}")
  public ResponseEntity<?> getSubmissionDetail(@PathVariable Long asmId, @PathVariable Long studentId) {
    log.info("update submission");
    return ResponseEntity.ok(submissionService.getSubmissionContent(asmId, studentId));
  }
}
