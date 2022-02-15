package com.btec.cms.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@Table(name = "course")
public class CourseEntity extends BaseEntity {

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String enrollCode;

  @Column private String description;

  @Column private Date startDate;

  @Column private Date endDate;

  @Column private String thumbnail;

  @Column private String status;

  @ManyToOne
  @JoinColumn(name = "topic_id", referencedColumnName = "id")
  private TopicEntity topicEntity;

  @ManyToOne
  @JoinColumn(name = "category_id", referencedColumnName = "id")
  private CategoryEntity categoryEntity;

  @ManyToMany(mappedBy = "courseEntityComment")
  private Set<UserEntity> userEntityComment;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(
      name = "student_courses",
      joinColumns = {@JoinColumn(name = "course_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "student_id", referencedColumnName = "id")})
  private Set<UserEntity> students;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "teacher_id", referencedColumnName = "id")
  private UserEntity teacher;
}
