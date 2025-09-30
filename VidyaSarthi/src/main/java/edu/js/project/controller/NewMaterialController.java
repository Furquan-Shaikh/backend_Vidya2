package edu.js.project.controller;

import edu.js.project.NewEntities.NewMaterial;
import edu.js.project.dto.*;
import edu.js.project.repository.NewRegulationRepo;
import edu.js.project.service.impl.RegulationMaterialsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/VidyaSarthi")
@CrossOrigin(origins = "http://localhost:5500")
public class NewMaterialController {

    private final RegulationMaterialsImpl service;

    @PostMapping("/addNewRegulation")
    public ResponseEntity<?> addRegulation(@RequestBody NewRegulationDto newRegulationDto) {

        service.addRegulation(newRegulationDto);
        return ResponseEntity.ok("Regulation Successfully added");

    }


    @PostMapping("/addNewNotes")
    public ResponseEntity<?> addNotes(
            @RequestParam("materialId") String materialId,
            @RequestParam("subjectCode") String subjectCode,
            @RequestParam("facultyId") String facultyId,
            @RequestParam("regulationId") String regulationId,
            @RequestParam("pdfName") String pdfName,
            @RequestParam("pdf") MultipartFile pdf) {

        UploadMaterialDto uploadMaterialDto = new UploadMaterialDto();
        UploadMaterialDto dto = UploadMaterialDto.builder()
                .materialId(materialId)
                .subjectCode(subjectCode)
                .facultyId(facultyId)
                .regulationId(regulationId)
                .pdf(pdf)
                .pdfName(pdfName)
                .build();

        service.uploadNotes(dto);
        return ResponseEntity.ok("Notes Uploaded Api");
    }

    @GetMapping("/getRegulationList")
    public ResponseEntity<?> getRegulation() {

        return ResponseEntity.ok(service.getRegulationList());

    }

    @PostMapping("/addNewPYQ")
    public ResponseEntity<?> addPYQ(@RequestParam("materialId") String materialId,
                                    @RequestParam("subjectCode") String subjectCode,
                                    @RequestParam("facultyId") String facultyId,
                                    @RequestParam("regulationId") String regulationId,
                                    @RequestParam("pdfName") String pdfName,
                                    @RequestParam("pdf") MultipartFile pdf) {

        UploadMaterialDto uploadMaterialDto = new UploadMaterialDto();
        UploadMaterialDto dto = UploadMaterialDto.builder()
                .materialId(materialId)
                .subjectCode(subjectCode)
                .facultyId(facultyId)
                .regulationId(regulationId)
                .pdf(pdf)
                .pdfName(pdfName)
                .build();
        service.uploadPyq(dto);
        return ResponseEntity.ok("PYQ Uploaded Api");

    }

    @PostMapping("/addNewQB")
    public ResponseEntity<?> addQB(@RequestParam("materialId") String materialId,
                                   @RequestParam("subjectCode") String subjectCode,
                                   @RequestParam("facultyId") String facultyId,
                                   @RequestParam("regulationId") String regulationId,
                                   @RequestParam("pdfName") String pdfName,
                                   @RequestParam("pdf") MultipartFile pdf) {

        UploadMaterialDto uploadMaterialDto = new UploadMaterialDto();
        UploadMaterialDto dto = UploadMaterialDto.builder()
                .materialId(materialId)
                .subjectCode(subjectCode)
                .facultyId(facultyId)
                .regulationId(regulationId)
                .pdfName(pdfName)
                .pdf(pdf)
                .build();
        service.uploadQb(dto);
        return ResponseEntity.ok("QB Uploaded Api");

    }

    @PostMapping("/addNewTeacher")
    public ResponseEntity<?> addTeacher(@RequestBody NewTeacherDto dto) {

        service.addTeacher(dto);
        return ResponseEntity.ok("Teacher added Successfully");

    }

    @GetMapping("/getMaterial/{materialId}")
    public ResponseEntity<?> getMaterial(@PathVariable String materialId) {

        return service.getMaterial(materialId);

    }


    @PostMapping("/getNewSubjectList")
    public ResponseEntity<?> getNewSubjectList(@RequestBody NewSubjectListDto dto) {


        SubjectListDto newSubjectList = service.getNewSubjectList(dto);
        return ResponseEntity.ok(newSubjectList);


    }

    @GetMapping("getMaterialList/{subjectCode}")
    public ResponseEntity<?> getMaterialListBySubjectCode(@PathVariable String subjectCode) {

        List<NewMaterialDto> materialListBySubjectCode = service.getMaterialListBySubjectCode(subjectCode);
        return ResponseEntity.ok(materialListBySubjectCode);

    }

    @GetMapping("getMaterialListPYQ/{subjectCode}")
    public ResponseEntity<?> getMaterialListPYQ(@PathVariable String subjectCode) {

        List<NewMaterialDto> materialListBySubjectCode = service.getMaterialListPYQ(subjectCode);
        return ResponseEntity.ok(materialListBySubjectCode);

    }

    @GetMapping("getMaterialListQB/{subjectCode}")
    public ResponseEntity<?> getMaterialListQB(@PathVariable String subjectCode) {

        List<NewMaterialDto> materialListBySubjectCode = service.getMaterialListByQB(subjectCode);
        return ResponseEntity.ok(materialListBySubjectCode);

    }

    @GetMapping("getMaterialListNOTES/{subjectCode}")
    public ResponseEntity<?> getMaterialListNOTES(@PathVariable String subjectCode) {

        List<NewMaterialDto> materialListBySubjectCode = service.getMaterialListByNotes(subjectCode);
        return ResponseEntity.ok(materialListBySubjectCode);

    }

    @GetMapping("getMaterialListAnnouncement/{subjectCode}")
    public ResponseEntity<?> getMaterialListAnnouncement(@PathVariable String subjectCode) {

        List<NewMaterialDto> materialListBySubjectCode = service.getMaterialListByAnnouncement(subjectCode);
        return ResponseEntity.ok(materialListBySubjectCode);

    }

    @PostMapping("/getFacultyId")
    public ResponseEntity<?> getFacultyId(@RequestBody EmailDto dto) {

        return ResponseEntity.ok(service.getFacultyId(dto));


    }

    @PostMapping("/faculty/getFacultyDetail")
    public ResponseEntity<?> getFacultyDetail(@RequestBody EmailDto dto) {

        return ResponseEntity.ok(service.getFacultyDetail(dto));


    }




}
