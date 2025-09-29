package edu.js.project.repository;

import edu.js.project.entity.Teacher;
import edu.js.project.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users,Long> {

    Optional<Users> findByEmail(String email);


    @Transactional
    @Modifying
    @Query("delete from Users u where u.teacher.id = :teacherId")
    int deleteByTeacherId(@Param("teacherId") Long teacherId);

    @Transactional
    @Modifying
    @Query("delete from Users u where u.student.id = :studentId")
    int deleteByStudentId(@Param("studentId") Long studentId);

    @Query("select u from Users u where u.roles = 'Faculty'")
    List<Users> getFacultyList();

    @Query("select u from Users u where u.roles = 'Student'")
    List<Users> getStudentList();



}
