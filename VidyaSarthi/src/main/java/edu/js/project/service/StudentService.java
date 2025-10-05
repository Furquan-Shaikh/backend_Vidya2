package edu.js.project.service;

import edu.js.project.dto.*;
import edu.js.project.entity.Complain;
import edu.js.project.entity.News;

import java.util.List;

public interface StudentService {

    void registerComplain(ComplainDto complain);
    boolean checkStudentStatus(String studentId);
    int getCurrentSemester(String studentId);
    List<SubjectDto> getSubjectBySemester(int Semester);
    List<MaterialDto> getNotesList(FindMaterialDto findMaterialDto);
    List<MaterialDto> getPYQList(FindMaterialDto findMaterialDto);
    List<MaterialDto> getQBList(FindMaterialDto findMaterialDto);
    byte[] getStudentPic(String studentId);

    List<News> getNewsAndAnnouncementsList();

    int getTotalMaterial();

    List<NewComplainDto> getComplainTable(String studentId);
}
