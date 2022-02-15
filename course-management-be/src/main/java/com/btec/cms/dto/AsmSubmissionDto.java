package com.btec.cms.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode
public class AsmSubmissionDto {
  private String fileUrl;
  private Date submitDate;
  private int submitStatus;
  private int gradeStatus;
  private float grade;
  private Date gradeDate;
  private Long userId;
  private Long asmId;
}
