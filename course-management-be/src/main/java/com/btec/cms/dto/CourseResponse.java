package com.btec.cms.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CourseResponse {
  private Long id;

  private String name;

  private String status;

  private Date createdDate;

  private String topicName;

  private String teacherName;
}
