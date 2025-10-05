package edu.js.project.controller;

import edu.js.project.dto.*;
import edu.js.project.entity.AdminClg;
import edu.js.project.entity.Base;
import edu.js.project.entity.Student;
import edu.js.project.entity.Teacher;
import edu.js.project.repository.StudentRepository;
import edu.js.project.repository.TeacherRepository;
import edu.js.project.service.UserService;
import edu.js.project.utility.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import edu.js.project.responseStructure.ResponseStructure;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/VidyaSarthi")
@CrossOrigin(origins = "http://localhost:5173")
public class VidyaSarthiController {

    private final UserService service;
    private final Mapper mapper;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {


        boolean verifyStatus = service.verifyUser(loginRequest.getEmail(), loginRequest.getPassword());

        if (verifyStatus) {
            return ResponseEntity.ok(new LoginResponse("Login successful"));
        } else {
            return ResponseEntity.ok(new LoginResponse("Login fail"));
        }

    }

    @PostMapping("/addStudent")
    public ResponseEntity<ResponseStructure<String>> addStudent(@RequestBody StudentDto req) {

        ResponseStructure<String> rs = new ResponseStructure<>();
        boolean isSuccessfullySignedUp = service.addUserToDB(req);
        if (isSuccessfullySignedUp) {
            rs.setStatus(HttpStatus.OK.value());
            rs.setMessage(String.format("%s account create", req.getName()));

            return new ResponseEntity<ResponseStructure<String>>(rs, HttpStatus.OK);
        } else {
            throw new RuntimeException("Username already exists");
        }

    }

    @PostMapping("/addFaculty")
    public ResponseEntity<?> addTeacher(@RequestBody TeacherDto teacherDto) {

        try {
            service.addUser(teacherDto);
            return ResponseEntity.ok(new LoginResponse("Added successful"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new LoginResponse("Faculty Added Failed"));
        }

    }

    @DeleteMapping("/removeUser")
    public ResponseEntity<?> removeUser(@RequestBody UserId req) {

        try {
            service.removeUser(req.getId());
            return ResponseEntity.ok("User Remove Successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/facultyList")
    public ResponseEntity<?> getFacultyList() {

        List<UserTableDto> facultyList = service.getFacultyList();
        return ResponseEntity.ok(facultyList);

    }

    @GetMapping("/studentList")
    public ResponseEntity<?> getStudentList() {

        List<StudentDto> studentList = service.getStudentList();
        return ResponseEntity.ok(studentList);

    }

    @PostMapping("/searchUser")
    public ResponseEntity<?> getUserData(@RequestBody UserId userId) {

        Base userDetail = service.getUserDetail(userId.getId());
        if (userDetail instanceof AdminClg user) {

            return ResponseEntity.ok(mapper.adminClgToAdminClgDto(user));

        } else if (userDetail instanceof Teacher user) {
            return ResponseEntity.ok(mapper.teacherToTeacherDto(user));

        } else if (userDetail instanceof Student user) {

            return ResponseEntity.ok(user);

        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/searchTeacherByFilter")
    public List<Teacher> searchTeacher(@RequestBody FacultyFilterDto filter) {
        List<String> designation = (filter.getDesignation() == null || filter.getDesignation().isEmpty())
                ? null : filter.getDesignation();

        List<String> subjects = (filter.getSubject() == null || filter.getSubject().isEmpty())
                ? null : filter.getSubject();

        return teacherRepository.findFacultiesByFilters(designation, subjects);

    }

    @PostMapping("/searchStudentByFilter")
    public List<Student> search(@RequestBody StudentFilterDto filter) {
        List<String> semesters = (filter.getSemesters() == null || filter.getSemesters().isEmpty())
                ? null : filter.getSemesters();

        List<Integer> years = (filter.getYears() == null || filter.getYears().isEmpty())
                ? null : filter.getYears();

        List<String> branches = (filter.getBranches() == null || filter.getBranches().isEmpty())
                ? null : filter.getBranches();

        return studentRepository.findStudentsByFilters(semesters, years, branches);

    }

//    @PostMapping("/updateStudent")
//    public ResponseEntity<?> updateStudent(@RequestBody StudentDto studentDto){
//
//
//        service.updateStudentDetail(studentDto);
//        return ResponseEntity.ok("Updated Successfully");
//
//    }


    @PostMapping(value = "/student/updateStudent", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateStudent(
            @ModelAttribute StudentDto studentDto,                                   // form fields -> DTO
            @RequestParam(value = "photo", required = false) MultipartFile photo     // file input named "photo"
    ) throws IOException {
        service.updateStudentDetail(studentDto, photo);
        return ResponseEntity.ok("Updated Successfully");
    }


    @PostMapping(value = "/faculty/updateFaculty", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateFaculty(@ModelAttribute NewTeacherDto teacherDto,
                                           @RequestParam(value = "photo", required = false) MultipartFile photo) throws IOException {


        service.updateFacultyDetail(teacherDto, photo);
        return ResponseEntity.ok("Updated Successfully");

    }

    @GetMapping("/addAdmin")
    public ResponseEntity<?> addAdminInfo() {

        System.out.println("I am inside add admin");
        service.addAdmin();
        return ResponseEntity.ok("Admin Added Successfully");

    }


}
