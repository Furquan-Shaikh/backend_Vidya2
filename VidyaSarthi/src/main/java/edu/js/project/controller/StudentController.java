package edu.js.project.controller;

import edu.js.project.dto.*;
import edu.js.project.entity.Complain;
import edu.js.project.service.StudentService;
import edu.js.project.service.impl.RegulationMaterialsImpl;
import edu.js.project.service.impl.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/VidyaSarthi/student")
public class StudentController {

    private final StudentService service;
    private final RegulationMaterialsImpl serviceMaterial;
    private final SubjectService subjectService;

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

    @GetMapping(value = "/getPic/{studentId}", produces = { "image/png", "image/jpeg", "application/octet-stream" })
    public ResponseEntity<byte[]> getStudentPic(@PathVariable String studentId) {
        try {
            byte[] img = service.getStudentPic(studentId);
            if (img == null || img.length == 0) {
                return ResponseEntity.noContent().build();
            }

            MediaType mediaType = MediaType.IMAGE_PNG;

            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .contentLength(img.length)
                    .body(img);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/getRegulationList")
    public ResponseEntity<?> getRegulation() {
        return ResponseEntity.ok(serviceMaterial.getRegulationList());
    }

    @PostMapping("/getNewSubjectList")
    public ResponseEntity<?> getNewSubjectList(@RequestBody NewSubjectListDto dto) {
        SubjectListDto newSubjectList = serviceMaterial.getNewSubjectList(dto);
        return ResponseEntity.ok(newSubjectList);
    }

    @GetMapping("/getAnnouncement")
    public ResponseEntity<?> getNewAndAnnouncementList(){
        return ResponseEntity.ok(service.getNewsAndAnnouncementsList());
    }

    @GetMapping("/getTotalMaterial")
    public ResponseEntity<?> totalMaterials(){
        return ResponseEntity.ok(service.getTotalMaterial());
    }

    @GetMapping("/getComplainTable/{studentId}")
    public ResponseEntity<?> getComplainTable(@PathVariable String studentId){
        return ResponseEntity.ok(service.getComplainTable(studentId));
    }

    @GetMapping("/getFacultyIdByMaterialId/{materialId}")
    public ResponseEntity<String> getFacultyIdByMaterialId(@PathVariable String materialId){
        return ResponseEntity.ok(serviceMaterial.getMaterialFacultyDetail(materialId));
    }

    @PostMapping("/getSubjectsWithFaculty")
    public ResponseEntity<?> getSubjectsWithFaculty(@RequestBody Map<String, Object> request) {
        try {
            String regulationId = (String) request.get("regulationId");
            String branch = (String) request.get("branch");
            Integer semester = (Integer) request.get("semester");

            if (regulationId == null || branch == null || semester == null) {
                return ResponseEntity.badRequest().body(
                        Map.of("success", false, "message", "Missing required parameters: regulationId, branch, semester")
                );
            }

            List<SubjectWithFacultyDTO> subjects = subjectService.getSubjectsWithFaculty(
                    regulationId, branch, semester
            );

            return ResponseEntity.ok(subjects);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(
                    Map.of("success", false, "message", e.getMessage())
            );
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    Map.of("success", false, "message", "Internal server error")
            );
        }
    }

    @GetMapping("/getFacultyBySubject/{subjectCode}")
    public ResponseEntity<?> getFacultyBySubject(@PathVariable String subjectCode) {
        try {
            Long facultyId = subjectService.getFacultyIdBySubjectCode(subjectCode);

            if (facultyId == null) {
                return ResponseEntity.ok(Map.of(
                        "success", true,
                        "facultyId", (Object) null,
                        "message", "No faculty assigned to this subject"
                ));
            }

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "facultyId", facultyId
            ));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(
                    Map.of("success", false, "message", e.getMessage())
            );
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    Map.of("success", false, "message", "Internal server error")
            );
        }
    }
}
