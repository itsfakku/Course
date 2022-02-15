package com.btec.cms.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.Set;

@Data
@EqualsAndHashCode
public class CourseDetailDto extends BaseDto {

  private String name;

  private String enrollCode;

  private String description;

  private Date startDate;

  private Date endDate;

  private String thumbnail;

  private Long topicId;

  private Long categoryId;

  private Long teacherId;

  private Set<Long> studentIds;
}
