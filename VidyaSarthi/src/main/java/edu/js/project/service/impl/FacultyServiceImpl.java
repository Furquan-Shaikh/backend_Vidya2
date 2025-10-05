package edu.js.project.service.impl;

import edu.js.project.NewEntities.NewSubject;
import edu.js.project.NewEntities.NewTeacher;
import edu.js.project.dto.*;
import edu.js.project.entity.*;
import edu.js.project.repository.*;
import edu.js.project.service.FacultyService;
import edu.js.project.utility.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FacultyServiceImpl implements FacultyService {

    private final Mapper mapper;
    private final TeacherRepository teacherRepository;
    private final NewsRepository newsRepository;
    private final MaterialRepository materialRepository;
    private final SemesterRepository semesterRepository;
    private final SubjectRepository subjectRepository;
    private final UnitRepository unitRepository;
    private final ComplainRepository complainRepository;
    private final NewTeacherRepo newTeacherRepo;
    private final NewMaterialRepo newMaterialRepo;
    private final NewSubjectRepo newSubjectRepo;

    public boolean checkFacultyStatus(String facultyId) {

        NewTeacher teacher = newTeacherRepo.findByFacultyId(facultyId).orElseThrow(
                () -> new RuntimeException("Faculty not found")
        );


        return checkStatus(teacher);

    }

    @Transactional
    @Override
    public void uploadNews(News news) {

        System.out.println(news.getFacultyId()+"*********************");
        News save = newsRepository.save(news);
        NewTeacher faculty = newTeacherRepo.findByFacultyId(news.getFacultyId()).orElseThrow(
                () -> new RuntimeException("Teacher Not Found")
        );
        save.setTeacher(faculty);


    }

    @Override
    public List<MaterialDto> getMaterialList() {
        return materialRepository.findAll().stream().map(mapper::materialToMaterialDto).toList();
    }

    @Override
    public UploadNotesDto getListSelection() {

        UploadNotesDto uploadNotesDto = new UploadNotesDto();
        List<SubjectDto> subjectList = subjectRepository.findAll().stream().map(mapper::subjectToSubjectDto).toList();
        Set<Integer> unitSet = unitRepository.findAll().stream().map(Unit::getUnitNumber).collect(Collectors.toSet());
        uploadNotesDto.setSubjectDto(subjectList);
        uploadNotesDto.setUnitSet(unitSet);

        return uploadNotesDto;

    }

    @Transactional
    @Override
    public void uploadNotes(UploadNoteDto uploadNoteDto) {

        Subject subject = subjectRepository.findBySubjectCode(uploadNoteDto.getSubjectCode()).orElseThrow(
                () -> new RuntimeException("Subject not found")
        );

        Unit unit = unitRepository.findUnitByQuery(uploadNoteDto.getSubjectCode(), uploadNoteDto.getUnitNo())
                .orElseThrow(() -> new RuntimeException("unit not found"));

        Teacher faculty = teacherRepository.findByFacultyId(uploadNoteDto.getFacultyId()).orElseThrow(
                () -> new RuntimeException("teacher not found")
        );

        Material.MaterialBuilder builder = Material.builder();
        Material notes = builder.title(uploadNoteDto.getTitle())
                .url(uploadNoteDto.getUrl())
                .type("Notes")
                .url(uploadNoteDto.getUrl())
                .unit(unit)
                .teacher(faculty)
                .subject(subject)
                .unitCode(unit.getUnitCode())
                .build();
        materialRepository.save(notes);

    }

    @Transactional
    @Override
    public void uploadQb(UploadNoteDto uploadNoteDto) {

        Subject subject = subjectRepository.findBySubjectCode(uploadNoteDto.getSubjectCode()).orElseThrow(
                () -> new RuntimeException("Subject not found")
        );


        Teacher faculty = teacherRepository.findByFacultyId(uploadNoteDto.getFacultyId()).orElseThrow(
                () -> new RuntimeException("teacher not found")
        );

        Material.MaterialBuilder builder = Material.builder();
        Material notes = builder.title(uploadNoteDto.getTitle())
                .url(uploadNoteDto.getUrl())
                .type("QB")
                .url(uploadNoteDto.getUrl())
                .teacher(faculty)
                .subject(subject)
                .build();
        materialRepository.save(notes);

    }

    @Transactional
    @Override
    public void uploadPyq(UploadNoteDto uploadNoteDto) {

        Subject subject = subjectRepository.findBySubjectCode(uploadNoteDto.getSubjectCode()).orElseThrow(
                () -> new RuntimeException("Subject not found")
        );


        Teacher faculty = teacherRepository.findByFacultyId(uploadNoteDto.getFacultyId()).orElseThrow(
                () -> new RuntimeException("teacher not found")
        );

        Material.MaterialBuilder builder = Material.builder();
        Material notes = builder.title(uploadNoteDto.getTitle())
                .url(uploadNoteDto.getUrl())
                .type("PYQ")
                .url(uploadNoteDto.getUrl())
                .teacher(faculty)
                .subject(subject)
                .build();
        materialRepository.save(notes);
    }

    @Override
    public void editMaterial(EditMaterialDto editMaterialDto) {

//        Material material = materialRepository.findById(editMaterialDto.getId()).orElseThrow(
//                () -> new RuntimeException("Material does not exists")
//        );
//
//        material.setUrl(editMaterialDto.getUrl());

    }

    @Override
    public List<ComplainDto> getComplainList() {
        return complainRepository.findAll().stream().map(mapper::complainToComplainDto).toList();
    }

    @Override
    public int totalNumberOfMaterial(String facultyID) {
        return newMaterialRepo.findMaterial(facultyID).size();
    }

    @Override
    public TeacherDto getFacultyInfoByEmail(String email) {
        return teacherRepository.findByEmail(email).map(mapper::teacherToTeacherDto).orElseThrow(
                () -> new RuntimeException("Teacher not found")
        );
    }

    @Override
    public SubjectListDto getNewSubjectList() {
        LinkedHashMap<String, String> collect = newSubjectRepo.findAll().stream().collect(Collectors.toMap(
                NewSubject::getSubjectCode,
                NewSubject::getName,
                (existing, replacement) -> existing,
                LinkedHashMap::new
        ));
        return SubjectListDto.builder().subject(collect).build();
    }

    @Transactional
    @Override
    public byte[] getFacultyPic(String facultyId) {
        return newTeacherRepo.findByFacultyId(facultyId).map(NewTeacher::getImageData).orElseThrow(() -> new RuntimeException("Faculty not found"));
    }


    private boolean checkStatus(NewTeacher teacher) {

        if (Objects.isNull(teacher.getName()))
            return false;
        if (Objects.isNull(teacher.getEmail()))
            return false;
        if (Objects.isNull(teacher.getAddress()))
            return false;
        if (Objects.isNull(teacher.getFacultyId()))
            return false;
        if (Objects.isNull(teacher.getPassword()))
            return false;
        if (Objects.isNull(teacher.getDesignation()))
            return false;
        if (Objects.isNull(teacher.getImageData()))
            return false;
        if (Objects.isNull(teacher.getPhone()))
            return false;
        return Objects.nonNull(teacher.getSubjects());
    }



}
