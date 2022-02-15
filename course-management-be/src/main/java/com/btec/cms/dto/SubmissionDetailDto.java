package com.btec.cms.dto;

import lombok.Data;

import java.util.Date;

@Data
public class SubmissionDetailDto {
  private String assignmentName;
  private Date submitDate;
  private int submitStatus;
  private float grade;
  private int gradeStatus;
  private Date gradeDate;
  private String fileUrl;
  private Date dueDate;
  private String timeRemaining;
  private String fileName;
  private String comment;
}
