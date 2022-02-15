package com.btec.cms.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "role")
public class RoleEntity extends BaseEntity {

  @Column(nullable = false)
  private String role;
}
