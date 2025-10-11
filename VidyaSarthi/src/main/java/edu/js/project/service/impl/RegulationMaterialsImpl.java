package edu.js.project.service.impl;

import edu.js.project.NewEntities.*;
import edu.js.project.dto.*;
import edu.js.project.entity.Users;
import edu.js.project.enums.BranchType;
import edu.js.project.enums.ComplainStatus;
import edu.js.project.enums.MaterialType;
import edu.js.project.repository.*;
import edu.js.project.service.RegulationMaterials;
import edu.js.project.utility.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegulationMaterialsImpl implements RegulationMaterials {

    private final NewMaterialRepo newMaterialRepo;
    private final NewTeacherRepo newTeacherRepo;
    private final NewRegulationRepo newRegulationRepo;
    private final UserRepository userRepository;
    private final NewSubjectRepo newSubjectRepo;
    private final NewComplainRepo newComplainRepo;
    private final StudentRepository studentRepository;
    private final PasswordEncoder encode;
    private final Mapper mapper;

    @Transactional
    @Modifying
    @Override
    public void uploadNotes(UploadMaterialDto uploadNoteDto) {

        NewTeacher faculty = newTeacherRepo.findByFacultyId(uploadNoteDto.getFacultyId()).orElseThrow(() -> new RuntimeException("Teacher not found"));
        NewRegulation regulation = newRegulationRepo.findByRegulationId(uploadNoteDto.getRegulationId()).orElseThrow(() -> new RuntimeException("Regulation not available"));


        NewMaterial build = NewMaterial.builder()
                .regulationId(uploadNoteDto.getRegulationId())
                .materialId(uploadNoteDto.getMaterialId())
                .materialType(MaterialType.NOTES)
                .subjectCode(uploadNoteDto.getSubjectCode())
                .teacher(faculty)
                .regulations(regulation)
                .facultyId(uploadNoteDto.getFacultyId())
                .pdfFilename(uploadNoteDto.getPdfName())
                .build();

        if (!(uploadNoteDto.getPdf() == null || uploadNoteDto.getPdf().isEmpty()))
            build.setMaterialData(savePdf(uploadNoteDto.getPdf()).getMaterialData());
        newMaterialRepo.save(build);

    }


    @Override
    @Transactional
    public void uploadQb(UploadMaterialDto uploadNoteDto) {

        NewTeacher faculty = newTeacherRepo.findByFacultyId(uploadNoteDto.getFacultyId()).orElseThrow(() -> new RuntimeException("Teacher not found"));
        NewRegulation regulation = newRegulationRepo.findByRegulationId(uploadNoteDto.getRegulationId()).orElseThrow(() -> new RuntimeException("Regulation not available"));


        NewMaterial build = NewMaterial.builder()
                .regulationId(uploadNoteDto.getRegulationId())
                .materialId(uploadNoteDto.getMaterialId())
                .materialType(MaterialType.QUESTION_BANK)
                .subjectCode(uploadNoteDto.getSubjectCode())
                .facultyId(uploadNoteDto.getFacultyId())
                .teacher(faculty)
                .regulations(regulation)
                .materialData(savePdf(uploadNoteDto.getPdf()).getMaterialData())
                .pdfFilename(uploadNoteDto.getPdfName())
                .build();

        if (!(uploadNoteDto.getPdf() == null || uploadNoteDto.getPdf().isEmpty()))
            build.setMaterialData(savePdf(uploadNoteDto.getPdf()).getMaterialData());
        newMaterialRepo.save(build);

    }

    @Override
    @Transactional
    public void uploadPyq(UploadMaterialDto uploadNoteDto) {

        NewTeacher faculty = newTeacherRepo.findByFacultyId(uploadNoteDto.getFacultyId()).orElseThrow(() -> new RuntimeException("Teacher not found"));
        NewRegulation regulation = newRegulationRepo.findByRegulationId(uploadNoteDto.getRegulationId()).orElseThrow(() -> new RuntimeException("Regulation not available"));


        NewMaterial build = NewMaterial.builder()
                .regulationId(uploadNoteDto.getRegulationId())
                .materialId(uploadNoteDto.getMaterialId())
                .materialType(MaterialType.PYQ)
                .subjectCode(uploadNoteDto.getSubjectCode())
                .facultyId(uploadNoteDto.getFacultyId())
                .teacher(faculty)
                .regulations(regulation)
                .pdfFilename(uploadNoteDto.getPdfName())
                .materialData(savePdf(uploadNoteDto.getPdf()).getMaterialData())
                .build();

        if (!(uploadNoteDto.getPdf() == null || uploadNoteDto.getPdf().isEmpty()))
            build.setMaterialData(savePdf(uploadNoteDto.getPdf()).getMaterialData());
        newMaterialRepo.save(build);

    }

    @Override
    public List<NewComplainDto> getAllComplains(String facultyId) {

        return newComplainRepo.findByFacultyId(facultyId).stream().map(mapper::newComplainToNewComplainDto).toList();

    }

    @Transactional
    @Override
    public void updateComplain(UpdatedStatusDto updatedStatus) {

        NewComplain newComplain = newComplainRepo.findById(updatedStatus.getId()).orElseThrow(() -> new RuntimeException("Complain not there to update"));
        newComplain.setComplainStatus(ComplainStatus.valueOf(updatedStatus.getUpdatedStatus()));

    }

    @Override
    public String getMaterialFacultyDetail(String materialId) {
        return newMaterialRepo.findFacultyIdByMaterialId(materialId);
    }

    @Transactional
    @Modifying
    public void addRegulation(NewRegulationDto dto) {

        NewRegulation build = NewRegulation.builder()
                .regulationId(dto.getRegulationId())
                .name(dto.getName())
                .newSubjects(dto.getNewSubjects())
                .build();

        newRegulationRepo.save(build);

    }

//    @Transactional
//    @Modifying
//    public void addTeacher(NewTeacherDto dto) {
//
//        String pass = encode.encode(dto.getPassword());
//
//        UsersDto facultyUser = new UsersDto();
//        facultyUser.setEmail(dto.getEmail());
//        facultyUser.setPassword(pass);
//        facultyUser.setRoles("Faculty");
//
//        Users users = mapper.usersDtoToUsers(facultyUser);
//        Users saveFaculty = userRepository.save(users);
//
//        Set<NewSubject> subjects = new HashSet<>();
//        if (dto.getSubjectCodes() != null) {
//            for (String sid : dto.getSubjectCodes()) {
//                NewSubject s = newSubjectRepo.findBySubjectCode(sid)
//                        .orElseThrow(() -> new RuntimeException("Subject not found: " + sid));
//                subjects.add(s);
//            }
//        }
//
//
//        NewTeacher build = NewTeacher.builder()
//                .email(dto.getEmail())
//                .name(dto.getName())
//                .designation(dto.getDesignation())
//                .phone(dto.getPhone())
//                .imageData(dto.getImageData())
//                .password(pass)
//                .facultyId(dto.getFacultyId())
//                .address(dto.getAddress())
//                .designation(dto.getDesignation())
//                .users(saveFaculty)
//                .subjects(subjects)
//                .build();
//
//
//        for (NewSubject s : subjects) {
//            s.getTeachers().add(build);
//        }
//        NewTeacher save = newTeacherRepo.save(build);
//
//        // link user -> teacher if needed
//        saveFaculty.setNewTeacher(save);
//        userRepository.save(saveFaculty);
//

    /// /        userRepository.save(saveFaculty);
//
//
//    }
    @Transactional
    public void addTeacher(NewTeacherDto dto) {
        // 1. Create and save the associated Users entity
        String pass = encode.encode(dto.getPassword());
        UsersDto facultyUser = new UsersDto(dto.getEmail(), pass, "Faculty");
        Users users = mapper.usersDtoToUsers(facultyUser);
        Users savedUser = userRepository.save(users);


        // 2. Fetch the managed NewSubject entities from the database
        Set<NewSubject> subjectsToAssociate = new HashSet<>();
        if (dto.getSubjectCodes() != null) {
            for (String code : dto.getSubjectCodes()) {
                NewSubject subject = newSubjectRepo.findBySubjectCode(code)
                        .orElseThrow(() -> new RuntimeException("Subject not found: " + code));
                subjectsToAssociate.add(subject);
            }
        }

        // 3. Create the NewTeacher entity (the owner of the relationship)
        NewTeacher teacher = NewTeacher.builder()
                .email(dto.getEmail())
                .name(dto.getName())
                .designation(dto.getDesignation())
                .phone(dto.getPhone())
                .imageData(dto.getImageData())
                .password(pass)
                .facultyId(dto.getFacultyId())
                .address(dto.getAddress())
                .users(savedUser)
                .build(); // Note: Don't set subjects via builder for clarity here

        // 4. Synchronize BOTH sides of the relationship
        // This is the key step: modify the collection on the OWNING entity.
        for (NewSubject subject : subjectsToAssociate) {
            teacher.getSubjects().add(subject);   // ✅ Owning side
            subject.getTeachers().add(teacher);   // ✅ Inverse side (for object graph consistency)
        }

        // 5. Save the OWNING entity. This persists the teacher AND the relationships
        // in the faculty_subject join table.
        NewTeacher savedTeacher = newTeacherRepo.save(teacher);

        // 6. Complete the link from User to Teacher
        savedUser.setNewTeacher(savedTeacher);
        userRepository.save(savedUser); // Ensures the foreign key is set in the users table
    }

    public NewMaterial savePdf(MultipartFile pdf) {


        NewMaterial res = new NewMaterial();
        try {
            if (pdf != null && !pdf.isEmpty()) {
                res.setMaterialData(pdf.getBytes());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return res;
    }


    public ResponseEntity<?> getMaterial(String materialId) {

        Optional<NewMaterial> optionalMaterial = newMaterialRepo.findByMaterialId(materialId);

        // Using .map() is a clean way to handle the Optional
        return optionalMaterial.map(material -> {
                    // Check if the actual PDF data exists in the entity
                    if (material.getMaterialData() == null || material.getMaterialData().length == 0) {
                        // If there's a record but no data, it's considered not found.
                        return ResponseEntity.notFound().build();
                    }

                    // Wrap the byte array in a ByteArrayResource for the response body
                    ByteArrayResource resource = new ByteArrayResource(material.getMaterialData());

                    // *** IMPORTANT CORRECTION ***
                    // Added .contentType(MediaType.APPLICATION_PDF)
                    // This header tells the browser what kind of file this is,
                    // so it knows to use a PDF viewer plugin. Without this, the browser
                    // might try to download the file or display it as garbled text.
                    return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + material.getPdfFilename() + "\"")
                            .contentType(MediaType.APPLICATION_PDF)
                            .contentLength(material.getMaterialData().length) // It's good practice to set the content length
                            .body(resource);
                })
                // If the optional was empty (no material found for the ID), return 404.
                .orElse(ResponseEntity.notFound().build());


    }

    @Transactional
    public List<NewRegulationDto> getRegulationList() {

        List<NewRegulation> all = newRegulationRepo.findAll();
        return all.stream().map(
                s -> {

                    return NewRegulationDto.builder()
                            .name(s.getName())
                            .regulationId(s.getRegulationId())
                            .newSubjects(s.getNewSubjects())
                            .build();

                }
        ).toList();
    }

    public SubjectListDto getNewSubjectList(NewSubjectListDto dto) {

        List<NewSubject> subjects = newSubjectRepo.findSubjectByFilter(dto.getRegulationId(), dto.getSemester(), BranchType.valueOf(dto.getBranch()));

        LinkedHashMap<String, String> subjectMap = subjects.stream()
                .collect(Collectors.toMap(
                        NewSubject::getSubjectCode,
                        NewSubject::getName,
                        (existing, replacement) -> existing,              // on duplicate keys, keep first
                        LinkedHashMap::new
                ));

        return SubjectListDto.builder()
                .subject(subjectMap)
                .build();
    }

    public List<NewMaterialDto> getMaterialListBySubjectCode(String subjectCode) {

        List<NewMaterial> bySubjectCode = newMaterialRepo.findBySubjectCode(subjectCode);
        return bySubjectCode.stream().map(mapper::newMaterialToNewMaterialDto).toList();

    }

    public List<NewMaterialDto> getMaterialListPYQ(String subjectCode) {

        List<NewMaterial> bySubjectCode = newMaterialRepo.findBySubjectCodeAndMaterialType(subjectCode, MaterialType.PYQ);
        return bySubjectCode.stream().map(mapper::newMaterialToNewMaterialDto).toList();

    }

    public List<NewMaterialDto> getMaterialListByQB(String subjectCode) {

        List<NewMaterial> bySubjectCode = newMaterialRepo.findBySubjectCodeAndMaterialType(subjectCode, MaterialType.QUESTION_BANK);
        return bySubjectCode.stream().map(mapper::newMaterialToNewMaterialDto).toList();

    }

    public List<NewMaterialDto> getMaterialListByNotes(String subjectCode) {

        List<NewMaterial> bySubjectCode = newMaterialRepo.findBySubjectCodeAndMaterialType(subjectCode, MaterialType.NOTES);
        return bySubjectCode.stream().map(mapper::newMaterialToNewMaterialDto).toList();

    }

    public List<NewMaterialDto> getMaterialListByAnnouncement(String subjectCode) {

        List<NewMaterial> bySubjectCode = newMaterialRepo.findBySubjectCodeAndMaterialType(subjectCode, MaterialType.ANNOUNCEMENT);
        return bySubjectCode.stream().map(mapper::newMaterialToNewMaterialDto).toList();

    }


    public EmailDto getFacultyId(EmailDto dto) {

        NewTeacher newTeacher = newTeacherRepo.findByEmail(dto.getEmail()).orElseThrow(() ->
                new RuntimeException("Teacher not found"));
        EmailDto d = new EmailDto();
        d.setFacultyId(newTeacher.getFacultyId());
        d.setEmail(newTeacher.getEmail());

        System.out.println("**************************" + newTeacher.getFacultyId() + "******************************");
        return d;

    }

    public NewTeacherDto getFacultyDetail(EmailDto dto) {

        NewTeacher faculty = newTeacherRepo.findByEmail(dto.getEmail()).orElseThrow(() ->
                new RuntimeException("Teacher not found"));
        return NewTeacherDto.builder()
                .name(faculty.getName())
                .address(faculty.getAddress())
                .facultyId(faculty.getFacultyId())
//                .materials(faculty.getMaterials())
                .phone(faculty.getPhone())
                .email(faculty.getEmail())
                .designation(faculty.getDesignation())
                .subjects(faculty.getSubjects())
                .imageData(faculty.getImageData())
                .build();


    }

    @Transactional
    public void editMaterial(String materialId, MultipartFile pdf) {

        NewMaterial material = newMaterialRepo.findByMaterialId(materialId).orElseThrow(
                () -> new RuntimeException("Material not found")
        );

        material.setMaterialData(savePdf(pdf).getMaterialData());


    }

    public List<EditMaterialDto> materialListByFaculty(String facultyId){

        return newMaterialRepo.findByFacultyId(facultyId).stream().map(material -> {

            EditMaterialDto edited = new EditMaterialDto();
            edited.setMaterialId(material.getMaterialId());
            edited.setPdfName(material.getPdfFilename());
            return edited;
        }).toList();


    }


    @Transactional
    public void addComplain(NewComplainDto dto) {

        studentRepository.findByStudentId(dto.getStudentId()).orElseThrow(() -> new RuntimeException("Student not found"));
        NewComplain newComplain = mapper.newComplainDtoToNewComplain(dto);
        newComplainRepo.save(newComplain);


    }
}
