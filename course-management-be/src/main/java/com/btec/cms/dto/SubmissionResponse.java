package com.btec.cms.dto;

import lombok.Data;

import java.util.Date;

@Data
public class SubmissionResponse {
  private Long studentId;
  private String studentName;
  private String fileName;
  private String fileUrl;
  private float grade;
  private int gradeStatus;
  private Date submitDate;
  private Date dueDate;
  private String submitStatus;
}
