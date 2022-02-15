package com.btec.cms.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "student_courses")
public class StudentCoursesEntity implements Serializable {
    @Id @Column private Long student_id;
    @Id @Column private Long course_id;

}
