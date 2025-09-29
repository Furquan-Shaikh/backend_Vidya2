package edu.js.project.service;

import edu.js.project.dto.ComplainDto;
import edu.js.project.dto.FindMaterialDto;
import edu.js.project.dto.MaterialDto;
import edu.js.project.dto.SubjectDto;
import edu.js.project.entity.Complain;

import java.util.List;

public interface StudentService {

    void registerComplain(ComplainDto complain);
    boolean checkStudentStatus(String studentId);
    int getCurrentSemester(String studentId);
    List<SubjectDto> getSubjectBySemester(int Semester);
    List<MaterialDto> getNotesList(FindMaterialDto findMaterialDto);
    List<MaterialDto> getPYQList(FindMaterialDto findMaterialDto);
    List<MaterialDto> getQBList(FindMaterialDto findMaterialDto);


}
