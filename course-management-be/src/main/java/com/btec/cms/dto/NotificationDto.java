package com.btec.cms.dto;

import javax.persistence.Column;
import java.util.Date;

public class NotificationDto extends BaseDto{

  private String notiContent;

  private Date createdDate;

  private int status;

  private String url;
}
