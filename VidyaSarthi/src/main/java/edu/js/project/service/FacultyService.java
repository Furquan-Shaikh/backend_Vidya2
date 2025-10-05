package edu.js.project.service;

import edu.js.project.dto.*;
import edu.js.project.entity.News;
import edu.js.project.responseStructure.SubCode;

import java.util.List;

public interface FacultyService {

    boolean checkFacultyStatus(String facultyId);
    void uploadNews(News news);
    List<MaterialDto> getMaterialList();
    UploadNotesDto getListSelection();
    void uploadNotes(UploadNoteDto uploadNoteDto);
    void uploadQb(UploadNoteDto uploadNoteDto);
    void uploadPyq(UploadNoteDto uploadNoteDto);
    void editMaterial(EditMaterialDto editMaterialDto);
    List<ComplainDto> getComplainList();
    int totalNumberOfMaterial(String facultyID);
    TeacherDto getFacultyInfoByEmail(String email);
    SubjectListDto getNewSubjectList();
    byte[] getFacultyPic(String facultyId);
}
