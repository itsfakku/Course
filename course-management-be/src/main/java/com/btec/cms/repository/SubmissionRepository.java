package com.btec.cms.repository;

import com.btec.cms.entity.AsmSubmissionEntity;
import com.btec.cms.entity.AssignmentEntity;
import com.btec.cms.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubmissionRepository extends JpaRepository<AsmSubmissionEntity, Long> {

  /**
   * Get submission detail by id an assignment id
   *
   * @param userId user id
   * @param asmId assignment id
   * @return
   */
  @Query(
      value =
          "SELECT NEW MAP(a.gradeStatus as gradeStatus"
              + ", a.grade as grade"
              + ", a.submitDate as submitDate"
              + ", a.fileUrl as fileUrl"
              + ", a.fileName as fileName"
              + ", a.gradeDate as gradeDate"
              + ", a.submitStatus as submitStatus"
              + ", asm.dueDate as dueDate"
              + ", asm.assignmentName as assignmentName"
              + ", a.comment as comment) "
              + "FROM AsmSubmissionEntity a "
              + "inner join AssignmentEntity asm on  a.assignment.Id = asm.Id "
              + "Inner join UserEntity u on  u.Id = a.user.Id "
              + "where u.Id=:userId and asm.Id=:asmId")
  Optional<Object> getSubmissionDetail(@Param("userId") Long userId, @Param("asmId") Long asmId);

  @Query(
      value =
          "select new map(u.Id as studentId, concat(u.firstName,' ', u.lastName) as studentName"
              + "     , a.fileUrl as fileUrl, a.fileName as fileName"
              + "     , asm.dueDate as dueDate "
              + "     , a.grade as grade "
              + "     ,a.submitDate as submitDate "
              + "     , a.gradeStatus as gradeStatus"
              + ", a.submitStatus as submitStatus) "
              + "from AsmSubmissionEntity a "
              + "         inner join AssignmentEntity asm on a.assignment.Id = asm.Id "
              + "         inner join UserEntity u on a.user.Id = u.Id where a.assignment.Id =:id")
  List<Object> getAllSubmissions(@Param("id") Long id);

  /**
   * Get submission by user and assignment
   *
   * @param asm assignment
   * @param user user
   * @return {@link Optional<AsmSubmissionEntity>} found submission
   */
  Optional<AsmSubmissionEntity> findAsmSubmissionEntityByAssignmentAndUser(
      AssignmentEntity asm, UserEntity user);

  @Query(
      value =
          "SELECT NEW MAP(u.Id as studentId, CONCAT(u.firstName,' ', u.lastName) AS studentName, a.fileUrl AS fileUrl) "
              + "FROM AsmSubmissionEntity a "
              + "INNER JOIN UserEntity u "
              + "ON u.Id = a.user.Id "
              + "INNER JOIN AssignmentEntity asm "
              + "ON asm.Id = a.assignment.Id "
              + "WHERE asm.Id =:asmId AND u.Id =:studentId")
  Object getSubmissionContent(@Param("asmId") Long asmId, @Param("studentId") Long studentId);
}
