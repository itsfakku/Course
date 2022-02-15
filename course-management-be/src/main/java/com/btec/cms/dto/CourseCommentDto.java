package com.btec.cms.dto;

import java.util.Date;

public class CourseCommentDto extends BaseDto{
  private String commentContent;
  private String parentId;
  private Date commentDate;
  private Long userId;
  private Long courseId;
}
