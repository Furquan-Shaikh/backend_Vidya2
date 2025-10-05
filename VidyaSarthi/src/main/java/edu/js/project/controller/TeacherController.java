package edu.js.project.controller;

import edu.js.project.dto.*;
import edu.js.project.entity.News;
import edu.js.project.responseStructure.GetInfo;
import edu.js.project.service.FacultyService;
import edu.js.project.service.RegulationMaterials;
import edu.js.project.service.impl.RegulationMaterialsImpl;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/VidyaSarthi/faculty")
public class TeacherController {

    private final FacultyService service;
    private final RegulationMaterialsImpl materialService;

    @GetMapping("/checkStatus/{facultyId}")
    public ResponseEntity<?> checkProfileStatus(@PathVariable String facultyId){

        boolean profileStatus = service.checkFacultyStatus(facultyId);
        if(profileStatus){
            return ResponseEntity.ok("Profile Completed");
        }else {
            return ResponseEntity.ok("Profile Incomplete");
        }


    }

    @GetMapping("/totalModule/{facultyId}")
    public ResponseEntity<?> getTotalUploadedMaterial(@PathVariable String facultyId){

        return ResponseEntity.ok(service.totalNumberOfMaterial(facultyId));

    }

    @PostMapping("/uploadNews&Announcement")
    public ResponseEntity<?> uploadNews(@RequestBody News news){

        System.out.println("************"+news.getFacultyId());
        service.uploadNews(news);
        return ResponseEntity.ok("NewsUploaded");

    }

    @GetMapping("/getMaterialList")
    public ResponseEntity<?> materialList(){

        return ResponseEntity.ok(service.getMaterialList());

    }

    @GetMapping("/backend/getNotesList")
    public ResponseEntity<?> getUploadNotesOptions(){

        UploadNotesDto listSelection = service.getListSelection();
        return ResponseEntity.ok(listSelection);

    }

    @PostMapping("/uploadNotes")
    public ResponseEntity<?> uploadNotes(@RequestBody UploadNoteDto upload){

        service.uploadNotes(upload);
        return ResponseEntity.ok("Notes uploaded");

    }
    @PostMapping("/uploadPYQ")
    public ResponseEntity<?> uploadPYQ(@RequestBody UploadNoteDto upload){

        service.uploadPyq(upload);
        return ResponseEntity.ok("PYQ Uploaded");

    }

    @PostMapping("/uploadQB")
    public ResponseEntity<?> uploadQB(@RequestBody UploadNoteDto upload){

        service.uploadQb(upload);
        return ResponseEntity.ok("QB Uploaded");

    }

//    @PostMapping("/editMaterial")
//    public ResponseEntity<?> editMaterial(@RequestBody EditMaterialDto upload){
//
//        service.editMaterial(upload);
//        return ResponseEntity.ok("Material Edited Successfully");
//
//    }

    @GetMapping("/complainList")
    public ResponseEntity<?>getComplainList(){

        List<ComplainDto> complainList = service.getComplainList();
        return ResponseEntity.ok(complainList);

    }

    @PostMapping("/getFacultyEmail")
    public ResponseEntity<?>getFacultyInfoUsingEmail(@RequestBody GetInfo getInfo){


        TeacherDto facultyInfoByEmail = service.getFacultyInfoByEmail(getInfo.getEmail());
        return ResponseEntity.ok(facultyInfoByEmail);

    }

    @GetMapping("/getMaterialListFaculty/{facultyId}")
    public ResponseEntity<?>getMaterialListByFaculty(@PathVariable("facultyId") String facultyId){

        List<EditMaterialDto> editMaterialDto = materialService.materialListByFaculty(facultyId);
        return ResponseEntity.ok(editMaterialDto);


    }

    @GetMapping("/getAllComplains/{facultyId}")
    public ResponseEntity<?> getAllComplains(@PathVariable("facultyId") String facultyId){

        return ResponseEntity.ok(materialService.getAllComplains(facultyId));

    }

    @PostMapping("/updateComplainStatus")
    public ResponseEntity<?> updateComplainStatus(@RequestBody UpdatedStatusDto updatedStatus){

        materialService.updateComplain(updatedStatus);
        return ResponseEntity.ok("Request Updated");

    }


    @GetMapping("/getSubjectList")
    public ResponseEntity<?> getSubjectList(){

        return ResponseEntity.ok(service.getNewSubjectList());

    }

    @GetMapping(value = "/getPic/{facultyId}", produces = { "image/png", "image/jpeg", "application/octet-stream" })
    public ResponseEntity<byte[]> getFacultyPic(@PathVariable String facultyId) {
        try {
            byte[] img = service.getFacultyPic(facultyId); // your service returns raw bytes or throws
            if (img == null || img.length == 0) {
                return ResponseEntity.noContent().build(); // 204 - frontend shows placeholder
            }

            // if you know actual type, use it; otherwise choose a default
            MediaType mediaType = MediaType.IMAGE_PNG; // or MediaType.IMAGE_JPEG

            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .contentLength(img.length)
                    .body(img);
        } catch (RuntimeException ex) { // faculty not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



}
