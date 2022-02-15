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
@Table(name = "conversation")
public class ConversationEntity extends BaseEntity {
  @Column private Date startedDate;

  @Column private String title;

  @ManyToMany(mappedBy = "conversationEntities")
  private Set<UserEntity> userEntities;

  @ManyToMany(mappedBy = "conversationEntityId")
  private Set<UserEntity> userEntityInConversation;
}
