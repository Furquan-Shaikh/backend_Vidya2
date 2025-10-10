package edu.js.project.repository;

import edu.js.project.NewEntities.NewTeacher;
import edu.js.project.dto.NewTeacherDto;
import edu.js.project.entity.Teacher;
import edu.js.project.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users,Long> {

    Optional<Users> findByEmail(String email);


    @Transactional
    @Modifying
    @Query("delete from Users u where u.newTeacher.id = :teacherId")
    int deleteByTeacherId(@Param("teacherId") Long teacherId);

    @Transactional
    @Modifying
    @Query("delete from Users u where u.student.id = :studentId")
    int deleteByStudentId(@Param("studentId") Long studentId);

    @Query("select u from Users u where u.roles = 'Faculty'")
    List<Users> getFacultyList();

    @Query("select u from Users u where u.roles = 'Student'")
    List<Users> getStudentList();

//    @Query("select f from ")
//    Optional<NewTeacher> findUserByFacultyId(@Param("facultyId") String facultyId);
    Optional<Users> findByNewTeacher_FacultyId(@Param("facultyId") String facultyId);

    boolean existsByEmail(String email);


}
