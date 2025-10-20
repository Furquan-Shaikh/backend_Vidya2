package edu.js.project.service.impl;

import edu.js.project.NewEntities.NewTeacher;
import edu.js.project.dto.*;
import edu.js.project.entity.Complain;
import edu.js.project.entity.News;
import edu.js.project.entity.Student;
import edu.js.project.entity.Unit;
import edu.js.project.repository.*;
import edu.js.project.service.StudentService;
import edu.js.project.utility.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service

public class StudentServiceImpl implements StudentService {

    private final Mapper mapper;
    private final StudentRepository studentRepository;
    private final SemesterRepository semesterRepository;
    private final SubjectRepository subjectRepository;
    private final ComplainRepository complainRepository;
    private final UnitRepository unitRepository;
    private final MaterialRepository materialRepository;
    private final NewsRepository newsRepository;
    private final NewMaterialRepo newMaterialRepo;
    private final NewComplainRepo newComplainRepo;

    @Override
    public void registerComplain(ComplainDto complain) {

        Complain complain1 = mapper.complainDtoToComplain(complain);
        complainRepository.save(complain1);

    }

    @Override
    public boolean checkStudentStatus(String studentId) {


        Student student = studentRepository.findByStudentId(studentId).orElseThrow(
                () -> new RuntimeException("Student not found")
        );


        return checkStatus(student);

    }

    @Override
    public int getCurrentSemester(String studentId) {

        Student student = studentRepository.findByStudentId(studentId).orElseThrow(
                () -> new RuntimeException("Student not found22222222")
        );
        String s = student.getSemester();
        if(Objects.nonNull(s)) {
            char c = s.charAt(0);
            return (c - '0');
        }else
            return 0;

    }

    @Override
    public List<SubjectDto> getSubjectBySemester(int semester) {

        return subjectRepository.findBySemesters_SemesterNumber(semester)
                .stream().map(mapper::subjectToSubjectDto)
                .toList();

    }

    @Override
    public List<MaterialDto> getNotesList(FindMaterialDto findMaterialDto) {

        Unit unit = unitRepository.findUnitByQuery(findMaterialDto.getSubjectCode(), findMaterialDto.getUnitNumber()).orElseThrow(
                () -> new RuntimeException("Unit Not Found")
        );

        return materialRepository.findByUnitCodeAndType(unit.getUnitCode(), "Notes")
                .stream().map(mapper::materialToMaterialDto)
                .toList();

    }

    @Override
    public List<MaterialDto> getPYQList(FindMaterialDto findMaterialDto) {

        return materialRepository.findBySubject_SubjectCodeAndType(findMaterialDto.getSubjectCode(), "PYQ")
                .stream().map(mapper::materialToMaterialDto)
                .toList();
    }

    @Override
    public List<MaterialDto> getQBList(FindMaterialDto findMaterialDto) {
        return materialRepository.findBySubject_SubjectCodeAndType(findMaterialDto.getSubjectCode(), "QB")
                .stream().map(mapper::materialToMaterialDto)
                .toList();
    }

    @Override
    public byte[] getStudentPic(String studentId) {
        return studentRepository.findByStudentId(studentId).map(Student::getImageData).orElseThrow(() -> new RuntimeException("Student not found"));

    }

    @Override
    public List<News> getNewsAndAnnouncementsList() {
        return newsRepository.findAll().stream().toList();
    }

    @Override
    public int getTotalMaterial() {
        return newMaterialRepo.findAll().size();
    }

    @Override
    public List<NewComplainDto> getComplainTable(String studentId) {
        return newComplainRepo.findByStudentId(studentId).stream().map(mapper::newComplainToNewComplainDto).toList();
    }

    private boolean checkStatus(Student student){

        if(Objects.isNull(student.getId()))
            return false;
        if(Objects.isNull(student.getAddress()))
            return false;
        if(Objects.isNull(student.getBranch()))
            return false;
        if(Objects.isNull(student.getEmail()))
            return false;
        if(Objects.isNull(student.getImageData()))
            return false;
        if(Objects.isNull(student.getName()))
            return false;
        if(Objects.isNull(student.getPassword()))
            return false;
        if(Objects.isNull(student.getPhone()))
            return false;
        if(Objects.isNull(student.getRegulation()))
            return false;
        if(Objects.isNull(student.getSemester()))
            return false;
        if(Objects.isNull(student.getStudentId()))
            return false;
        return Objects.nonNull(student.getYear());


    }



}
