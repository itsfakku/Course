package com.btec.cms.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@Table(name = "assignment")
public class AssignmentEntity extends BaseEntity {
  @Column(nullable = false)
  private Date dueDate;

  @Column private String assignmentName;

  @Column private String fileFormat;

  @Column private int fileQuantity;

  @Column private String feedbackContent;

  @ManyToOne
  @JoinColumn(name = "course", referencedColumnName = "id")
  private CourseEntity courseEntityAssigment;

  @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL)
  private Set<AsmSubmissionEntity> users;

  @ManyToMany(mappedBy = "assignmentEntityComment")
  private Set<UserEntity> userEntityComment;
}
