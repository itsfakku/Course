package com.btec.cms.dto;

import lombok.Data;

@Data
public class CourseAssignedToStudent {
    private String courseId;
    private String courseName;
    private String teacherName;
    private float grade;
    private String status;

}
