package com.btec.cms.repository;

import com.btec.cms.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

  /**
   * Find user by username
   *
   * @param username
   * @return
   */
  UserEntity findByUsername(String username);

  UserEntity getById(Long id);

  @Query(
      value =
          "SELECT MAX(CAST(REPLACE(UPPER(username), UPPER(:username), '') AS UNSIGNED)) AS numbers "
              + "FROM user WHERE username LIKE CONCAT(:username, '%');",
      nativeQuery = true)
  Optional<Integer> findMaxUsernamePostfix(@Param("username") String username);

  /**
   * Check user with exist email
   *
   * @param email user email
   * @return {@link Boolean}
   */
  Boolean existsByEmail(String email);

  /**
   * Get 10 newest created users
   *
   * @return {@link List<UserEntity>} List 10 newest created users
   */
  @Query(value = "SELECT * FROM user ORDER BY created_date DESC limit 10", nativeQuery = true)
  List<UserEntity> get10NewestUsers();

  /**
   * Get user by id and role id
   *
   * @param id user id
   * @param roleId user role id
   * @return {@link Optional<UserEntity>} user found
   */
  @Query(value = "SELECT * FROM user WHERE  id = :id AND role = :roleId", nativeQuery = true)
  Optional<UserEntity> findByIdAndRoleId(
      @Param("id") @NotNull Long id, @Param("roleId") @NotNull Long roleId);

  /**
   * Get users by role
   *
   * @param role users role
   * @return {@link List<UserEntity>} list users found
   */
  @Query("SELECT u FROM UserEntity u where u.roleEntity.role = :role")
  List<UserEntity> getUserByRole(String role);

  /**
   * Get total amount of users with topic id
   *
   * @param id topic id
   * @return int value total record
   */
  @Query(value = "SELECT count(*) FROM user_topic WHERE topic_id   = :id", nativeQuery = true)
  int getTotalCountByTopicId(@Param("id") Long id);

  /**
   * Get Full Name of teacher in course
   *
   * @param id course id
   * @return {@link String} full name of teacher
   */
  @Query(
      value =
          " SELECT  CONCAT_WS(\" \", u.first_name, u.last_name) AS teacher_name  FROM user u \n"
              + "    INNER JOIN course c \n"
              + "    ON c.teacher_id = u.id \n"
              + "    WHERE c.id = :id",
      nativeQuery = true)
  String getUserNameByCourseId(@Param("id") Long id);

  /**
   * Get full name of user by id
   *
   * @param id user id
   * @return full name of user
   */
  @Query(
      value =
          "SELECT CONCAT_WS(\" \", u.first_name, u.last_name) = fullname FROM user u WHERE u.id =:id",
      nativeQuery = true)
  String getFullNameById(Long id);

  /**
   * Get all teacher in topic
   *
   * @param id topic id
   * @return {@link List<UserEntity>} List teachers in topic
   */
  @Query(
      value =
          " SELECT u.* "
              + "  from user u"
              + "  inner join user_topic t on u.id = t.user_id"
              + "  inner join topic p on p.id = t.topic_id where p.id =:id",
      nativeQuery = true)
  List<UserEntity> getUserByTopic(Long id);

  /**
   * Get all students in course
   *
   * @param id course id
   * @return {@link List<UserEntity>} List students in topic
   */
  @Query(
      value =
          " SELECT u.* FROM user u inner join student_courses s on u.id = s.student_id inner join course c on c.id = s.course_id where c.id =:id",
      nativeQuery = true)
  List<UserEntity> getStudentInCourse(Long id);
}
