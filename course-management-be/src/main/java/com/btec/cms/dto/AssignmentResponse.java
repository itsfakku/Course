package com.btec.cms.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode
public class AssignmentResponse extends BaseDto {

  private String assignmentName;

  private Date dueDate;
}
