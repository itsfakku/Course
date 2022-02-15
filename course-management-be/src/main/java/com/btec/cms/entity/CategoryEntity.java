package com.btec.cms.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "category")
public class CategoryEntity extends BaseEntity {
  @Column private String name;
  @Column private String image;
  @Column private String description;
}
