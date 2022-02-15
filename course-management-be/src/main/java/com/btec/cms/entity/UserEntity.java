package com.btec.cms.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "user")
@Data
public class UserEntity extends BaseEntity {

  @Column(nullable = false, length = 50)
  private String username;

  @Column(nullable = false, length = 60)
  private String password;

  @Column(nullable = false)
  private String email;

  @Column private String firstName;

  @Column private String lastName;

  @Column private String avatar;

  @Column private String phone;

  @Column private String address;

  @Column private Date dob;

  @Column private int status;

  @ManyToOne
  @JoinColumn(name = "role", referencedColumnName = "id")
  private RoleEntity roleEntity;

  @ManyToMany(mappedBy = "users")
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  private Set<TopicEntity> topics;

  @ManyToMany
  @JoinTable(
      name = "user_notification",
      joinColumns = @JoinColumn(name = "user_Id"),
      inverseJoinColumns = @JoinColumn(name = "notification_Id"))
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  private Set<NotificationEntity> notificationEntities;

  @ManyToMany
  @JoinTable(
      name = "message",
      joinColumns = @JoinColumn(name = "sender_Id"),
      inverseJoinColumns = @JoinColumn(name = "conversation_Id"))
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  private Set<ConversationEntity> conversationEntities;

  @ManyToMany(mappedBy = "students")
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  private Set<CourseEntity> student_courses;

  @ManyToMany
  @JoinTable(
      name = "course_comment",
      joinColumns = @JoinColumn(name = "user_Id"),
      inverseJoinColumns = @JoinColumn(name = "course_Id"))
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  private Set<CourseEntity> courseEntityComment;

  @ManyToMany
  @JoinTable(
      name = "asm_comment",
      joinColumns = @JoinColumn(name = "user_Id"),
      inverseJoinColumns = @JoinColumn(name = "asm_Id"))
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  private Set<AssignmentEntity> assignmentEntityComment;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  private Set<AsmSubmissionEntity> assignments;

  @ManyToMany
  @JoinTable(
      name = "participant",
      joinColumns = @JoinColumn(name = "user_Id"),
      inverseJoinColumns = @JoinColumn(name = "conversation_Id"))
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  private Set<ConversationEntity> conversationEntityId;
}
