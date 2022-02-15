package com.btec.cms.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserDto extends BaseDto {
  private String username;
  private String email;
  private String firstName;
  private String lastName;
  private String avatar;
  private String phone;
  private String address;
  private Date dob;
  private int status;
  private String role;
}
