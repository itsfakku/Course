package com.btec.cms.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode
public class TopicDto extends BaseDto {
  private String name;
  private String description;
  private Set<Long> teacherIds;
}
