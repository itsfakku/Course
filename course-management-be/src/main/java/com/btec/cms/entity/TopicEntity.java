package com.btec.cms.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "topic")
public class TopicEntity extends BaseEntity {
  @Column(nullable = false)
  private String name;

  @Column private String description;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(
      name = "user_topic",
      joinColumns = {@JoinColumn(name = "topic_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")})
  private Set<UserEntity> users;
}
