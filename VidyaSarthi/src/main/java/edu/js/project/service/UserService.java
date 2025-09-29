package edu.js.project.service;

import edu.js.project.dto.*;
import edu.js.project.entity.*;

import java.util.List;

public interface UserService {

    public void addUser(TeacherDto teacherDto);
    public void addUser(StudentDto studentDto);
    public void addAdmin();
    public void removeUser(long userId);
    public boolean verifyUser(String email, String password);
    public List<TeacherDto> getFacultyList();
    public List<StudentDto> getStudentList();
    public <T extends Base> T getUserDetail(long userId);
    public void addRegulation(RegulationInputDto input);
    public void updateStudentDetail(StudentDto studentDto);
    public void updateFacultyDetail(TeacherDto teacherDto);
    public void addUnit(UnitDto dto);
    public void addMaterial(MaterialDto dto, String subCode, String facultyId, String unitCode);
    public boolean addUserToDB(StudentDto req);
    public UsersDto getUserDetail(String email);




}
