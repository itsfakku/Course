package com.btec.cms.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TopicResponse extends BaseDto{
  private Long id;
  private String name;
  private String description;
}
