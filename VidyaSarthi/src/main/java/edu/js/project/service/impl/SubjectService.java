package edu.js.project.service.impl;

import edu.js.project.NewEntities.NewSubject;
import edu.js.project.NewEntities.NewTeacher;
import edu.js.project.dto.SubjectWithFacultyDTO;
import edu.js.project.enums.BranchType;
import edu.js.project.repository.NewSubjectRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final NewSubjectRepo subjectRepository;

    public List<SubjectWithFacultyDTO> getSubjectsWithFaculty(String regulationId, String branch, int semester) {
        try {
            BranchType branchType = BranchType.valueOf(branch.toUpperCase());

            List<NewSubject> subjects = subjectRepository.findSubjectsWithFaculty(
                    regulationId, branchType, semester
            );

            List<SubjectWithFacultyDTO> result = new ArrayList<>();

            for (NewSubject subject : subjects) {
                NewTeacher teacher = subject.getTeachers().stream()
                        .findFirst()
                        .orElse(null);

                SubjectWithFacultyDTO dto = SubjectWithFacultyDTO.builder()
                        .subjectCode(subject.getSubjectCode())
                        .subjectName(subject.getName())
                        .build();

                if (teacher != null) {
                    dto.setFacultyId(teacher.getId());
                    dto.setFacultyIdString(teacher.getFacultyId());
                    dto.setFacultyName(teacher.getName());
                    dto.setFacultyEmail(teacher.getEmail());
                    dto.setFacultyDesignation(teacher.getDesignation());
                }

                result.add(dto);
            }

            return result;

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid branch: " + branch);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch subjects with faculty", e);
        }
    }

    public Long getFacultyIdBySubjectCode(String subjectCode) {
        NewSubject subject = subjectRepository.findBySubjectCode(subjectCode)
                .orElseThrow(() -> new IllegalArgumentException("Subject not found: " + subjectCode));

        NewTeacher teacher = subject.getTeachers().stream()
                .findFirst()
                .orElse(null);

        if (teacher == null) {
            return null;
        }

        return teacher.getId();
    }
}
