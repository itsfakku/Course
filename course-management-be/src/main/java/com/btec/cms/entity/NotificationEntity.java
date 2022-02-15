package com.btec.cms.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@Table(name = "notification")
public class NotificationEntity extends BaseEntity {

  @Column(nullable = false)
  private String notiContent;

  @Column private int status;

  @Column private String url;

  @ManyToMany(mappedBy = "notificationEntities")
  private Set<UserEntity> userEntities;
}
