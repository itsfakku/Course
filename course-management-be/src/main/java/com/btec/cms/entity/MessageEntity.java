package com.btec.cms.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@Table(name = "message")
public class MessageEntity extends BaseEntity {

  @Column private String content;

  @Column private Date date;
}
