package com.btec.cms.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "asm_submission")
public class AsmSubmissionEntity {

  @EmbeddedId AsmUserId id;

  @Column(unique = true)
  private String fileUrl;

  @Column private String fileName;

  @Column private Date submitDate;

  @Column private int submitStatus;

  @Column private int gradeStatus;

  @Column private float grade;

  @Column private Date gradeDate;

  @Column(columnDefinition = "TEXT")
  private String comment;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @MapsId("asmId")
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  private AssignmentEntity assignment;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @MapsId("userId")
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  private UserEntity user;
}
