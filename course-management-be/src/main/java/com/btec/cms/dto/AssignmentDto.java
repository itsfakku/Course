package com.btec.cms.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class AssignmentDto extends BaseDto {
  private String assignmentName;
  private Date dueDate;
  private String fileFormat;
  private int fileQuantity;
  private String feedbackContent;
  private Long courseId;
}
