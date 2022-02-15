package com.btec.cms.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class AsmUserId implements Serializable {

  @Column(name = "userId")
  private Long userId;

  @Column(name = "asmId")
  private Long asmId;
}
