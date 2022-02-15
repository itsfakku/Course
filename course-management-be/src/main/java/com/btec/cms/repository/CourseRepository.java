package com.btec.cms.repository;

import com.btec.cms.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Long> {

  /**
   * Get total amount of course with category id
   *
   * @param id category id
   * @return int value total record
   */
  @Query("SELECT count(*) FROM CourseEntity c WHERE c.categoryEntity.Id = :id")
  int getTotalCountByCategoryId(@Param("id") Long id);

  /**
   * Get total amount of course with topic id
   *
   * @param id topic id
   * @return int value total record
   */
  @Query("SELECT count(*) FROM CourseEntity c WHERE c.topicEntity.Id = :id")
  int getTotalCountByTopicId(@Param("id") Long id);

  /**
   * Get all courses
   *
   * @return {@link List<Object>} list courses
   */
  @Query(
      value =
          "SELECT NEW map(c.Id as id"
              + ", c.name as name"
              + ", c.status as status"
              + ", c.createdDate as createdDate"
              + ", t.name as topicName"
              + ", u.username as teacherName)"
              + " FROM CourseEntity c "
              + " JOIN TopicEntity t "
              + "ON c.topicEntity.Id = t.Id "
              + " JOIN UserEntity u "
              + "ON c.teacher.Id = u.Id")
  List<Object> getAllCourses();

  /**
   * Check exists course
   *
   * @param name course name
   * @return if courses exist or not
   */
  boolean existsByName(String name);

  /**
   * Get 2 latest courses
   *
   * @return {@link List<CourseEntity>} 2 latest courses
   */
  List<CourseEntity> findTop2ByOrderByCreatedDateDesc();

  /**
   * Get all course assigned to teacher
   *
   * @param id teacher id
   * @return {@link List<CourseEntity> } list courses assigned to teacher
   */
  @Query(
      value =
          "SELECT NEW MAP(c.Id as id"
              + ", c.name as name"
              + ", c.status as status)"
              + " FROM CourseEntity c "
              + " JOIN UserEntity u "
              + " ON c.teacher.Id = u.Id"
              + " WHERE c.teacher.Id =:id")
  List<Object> getAllCoursesAssignedToTeacher(@Param("id") Long id);

  /**
   * Get all courses of student
   *
   * @param studentId student id
   * @return {@link List<Object>}
   */
  @Query(
      value =
          "SELECT NEW map( c.Id AS courseId, c.name AS courseName, concat(us.firstName, ' ', us.lastName) AS teacherName, AVG(sm.grade) AS grade )"
              + " FROM  StudentCoursesEntity st"
              + " LEFT JOIN CourseEntity c on st.course_id = c.Id"
              + " LEFT JOIN UserEntity u on u.Id = st.student_id"
              + " LEFT JOIN AssignmentEntity a on c.Id = a.courseEntityAssigment.Id "
              + " LEFT JOIN AsmSubmissionEntity sm on a.Id = sm.assignment.Id AND  sm.user.Id= u.Id"
              + " LEFT JOIN UserEntity us on us.Id= c.teacher.Id"
              + " WHERE st.student_id =:studentId \n"
              + " GROUP BY st.course_id")
  List<Object> getAllCoursesOfStudent(@Param("studentId") Long studentId);
}
