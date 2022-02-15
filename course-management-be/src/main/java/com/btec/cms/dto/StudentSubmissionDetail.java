package com.btec.cms.dto;

import lombok.Data;

@Data
public class StudentSubmissionDetail {
  private Long studentId;
  private String studentName;
  private String fileUrl;
}
