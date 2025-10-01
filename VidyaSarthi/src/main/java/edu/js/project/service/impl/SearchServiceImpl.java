package edu.js.project.service.impl;

import edu.js.project.NewEntities.NewTeacher;
import edu.js.project.dto.StudentDto;
import edu.js.project.dto.TeacherDto;
import edu.js.project.entity.Student;
import edu.js.project.entity.Teacher;
import edu.js.project.entity.Users;
import edu.js.project.repository.NewTeacherRepo;
import edu.js.project.repository.StudentRepository;
import edu.js.project.repository.TeacherRepository;
import edu.js.project.repository.UserRepository;
import edu.js.project.service.SearchService;
import edu.js.project.utility.Mapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final NewTeacherRepo newTeacherRepo;
    private final UserRepository userRepository;
    private final Mapper mapper;

    @Override
    public StudentDto searchStudentByStudentId(String studentId) {

        Student student = studentRepository.findByStudentId(studentId).orElseThrow(
                () -> new RuntimeException("Student not found")
        );
         return mapper.studentToStudentDto(student);

    }

    @Override
    public TeacherDto searchFacultyByFacultyId(String facultyId) {
        return teacherRepository.findByFacultyId(facultyId)
                .map(mapper::teacherToTeacherDto)
                .orElseThrow( () -> new RuntimeException("Faculty not found"));
    }

    @Transactional
    @Override
    public void deleteFaculty(String facultyId) {

        NewTeacher teacher = newTeacherRepo.findByFacultyId(facultyId).orElseThrow(
                () -> new RuntimeException("Faculty not found")
        );

        Long userId = teacher.getId();
        int deleted = userRepository.deleteByTeacherId(userId);
        if (deleted == 0) throw new RuntimeException("Faculty not present in user table");


    }

    @Transactional
    @Override
    public void deleteStudent(String studentId) {

        Student std = studentRepository.findByStudentId(studentId).orElseThrow(
                () -> new RuntimeException("Student not found")
        );

        Long userId = std.getId();
        int deleted = userRepository. deleteByStudentId(userId);
        if (deleted == 0) throw new RuntimeException("Student not present in user table");


    }
}
