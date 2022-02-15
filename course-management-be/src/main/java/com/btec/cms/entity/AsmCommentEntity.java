package com.btec.cms.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@Table(name = "asm_comment")
public class AsmCommentEntity extends BaseEntity {
  @Column private String commentContent;

  @Column private Date commentDate;
}
