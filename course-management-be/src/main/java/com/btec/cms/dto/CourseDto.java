package com.btec.cms.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode
public class CourseDto extends BaseDto {

  private String name;

  private String enrollCode;

  private String description;

  private Date startDate;

  private Date endDate;

  private String thumbnail;

  private Long topicId;

  private Long categoryId;

  private String status;

  private Long teacherId;
}
