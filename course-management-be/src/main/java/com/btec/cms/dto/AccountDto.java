package com.btec.cms.dto;

import com.btec.cms.entity.RoleEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AccountDto extends BaseDto{
  private String username;
  private String password;
  private RoleEntity role;
  private String firstName;
  private String lastName;
  private int status;
}
