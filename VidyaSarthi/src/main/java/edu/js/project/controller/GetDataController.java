package edu.js.project.controller;

import edu.js.project.dto.StudentDto;
import edu.js.project.dto.TeacherDto;
import edu.js.project.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/VidyaSarthi")
public class GetDataController {

    private final SearchService service;
    
    @GetMapping("/searchByStudentId/{studentId}")
    public ResponseEntity<?> searchStudentByStudentId(@PathVariable String studentId){

        StudentDto studentDto = service.searchStudentByStudentId(studentId);
        return ResponseEntity.ok(studentDto);

    }

    @DeleteMapping("/deleteFaculty/{facultyId}")
    public ResponseEntity<?> deleteFacultyByFacultyID(@PathVariable String facultyId){

        service.deleteFaculty(facultyId);
        return ResponseEntity.ok("Delete successfully");

    }

    @DeleteMapping("/deleteStudent/{studentId}")
    public ResponseEntity<?> deleteStudentByStudentID(@PathVariable String studentId){

        service.deleteStudent(studentId);
        return ResponseEntity.ok("Delete successfully");

    }

    @GetMapping("/searchByFacultyId/{facultyId}")
    public ResponseEntity<?> searchFacultyByFacultyId(@PathVariable String facultyId){

        TeacherDto teacherDto = service.searchFacultyByFacultyId(facultyId);
        return ResponseEntity.ok(teacherDto);

    }

}
