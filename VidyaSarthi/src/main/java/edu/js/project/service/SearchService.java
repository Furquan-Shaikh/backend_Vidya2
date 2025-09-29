package edu.js.project.service;

import edu.js.project.dto.MaterialDto;
import edu.js.project.dto.StudentDto;
import edu.js.project.dto.TeacherDto;

import java.util.List;

public interface SearchService {

    StudentDto searchStudentByStudentId(String studentId);
    TeacherDto searchFacultyByFacultyId(String facultyId);
    void deleteFaculty(String facultyId);
    void deleteStudent(String studentId);



}
