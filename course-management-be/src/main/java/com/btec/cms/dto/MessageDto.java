package com.btec.cms.dto;

import java.util.Date;

public class MessageDto extends BaseDto{
  private String content;
  private Date date;
  private Long senderId;
  private Long conversationId;
}
