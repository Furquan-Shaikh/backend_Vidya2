package edu.js.project.controller;

import edu.js.project.dto.*;
import edu.js.project.entity.Complain;
import edu.js.project.service.StudentService;
import edu.js.project.service.impl.RegulationMaterialsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/VidyaSarthi/student")
public class StudentController {

    private final StudentService service;
    private final RegulationMaterialsImpl serviceMaterial;


    @PostMapping("/registerComplain")
    public ResponseEntity<?>complainMaterial(@RequestBody ComplainDto complain){

        service.registerComplain(complain);
        return ResponseEntity.ok("Complain register");

    }

    @GetMapping("/checkStatus/{studentId}")
    public ResponseEntity<?> checkProfileStatus(@PathVariable String studentId){

        boolean profileStatus = service.checkStudentStatus(studentId);
        if(profileStatus){
            return ResponseEntity.ok("Profile Completed");
        }else {
            return ResponseEntity.ok("Profile Incomplete");
        }


    }

    @GetMapping("/getCurrentSemester/{studentId}")
    public ResponseEntity<?> getCurrentSemester(@PathVariable String studentId){


        int currentSemester = service.getCurrentSemester(studentId);
        return ResponseEntity.ok(currentSemester);

    }

    @GetMapping("/getSubjectBySemester/{semesterNo}")
    public ResponseEntity<?> getSubjectBySemesterFilter(@PathVariable int semesterNo){

        List<SubjectDto> subjectBySemester = service.getSubjectBySemester(semesterNo);
        return ResponseEntity.ok(subjectBySemester);

    }

    @PostMapping("/findNotes")
    public ResponseEntity<?> getNotesList(@RequestBody FindMaterialDto findMaterialDto){

        List<MaterialDto> dto = service.getNotesList(findMaterialDto);
        return ResponseEntity.ok(dto);

    }

    @PostMapping("/findPYQ")
    public ResponseEntity<?> getPYQList(@RequestBody FindMaterialDto findMaterialDto){

        List<MaterialDto> dto = service.getPYQList(findMaterialDto);
        return ResponseEntity.ok(dto);

    }

    @PostMapping("/findQB")
    public ResponseEntity<?> getQBList(@RequestBody FindMaterialDto findMaterialDto){

        List<MaterialDto> dto = service.getPYQList(findMaterialDto);
        return ResponseEntity.ok(dto);

    }

    @PostMapping("/registerNewComplain")
    public ResponseEntity<?> registerComplain(@RequestBody NewComplainDto dto){

        serviceMaterial.addComplain(dto);
        return ResponseEntity.ok("Complain raised successfully");

    }

}
