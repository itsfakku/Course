package com.btec.cms.repository;

import com.btec.cms.entity.AssignmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<AssignmentEntity, Long> {

  /**
   * Get all assignments in course
   *
   * @param id course id
   * @return {@link List<Object>} list assignments in course
   */
  @Query(
      value =
          "SELECT NEW map(a.Id as id,a.assignmentName as assignmentName, a.dueDate as dueDate,a.createdDate as createdDate, a.modifiedDate as modifiedDate ) FROM AssignmentEntity a "
              + "INNER JOIN CourseEntity c ON c.Id = a.courseEntityAssigment.Id WHERE c.Id=:id")
  List<Object> getALlAssignmentsInCourse(@Param("id") Long id);

  @Query(
      value =
          "SELECT count(a.assignmentName) FROM AssignmentEntity a WHERE a.assignmentName =:name and a.courseEntityAssigment.Id =:courseId")
  int countAssignmentName(@Param("name") String name, @Param("courseId") Long courseId);
}
