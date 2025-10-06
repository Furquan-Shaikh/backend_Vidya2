package edu.js.project.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class FacultyListWithSubject {

    private String facultyId;
    private String name;
    private String email;
    private String designation;
    private List<String> subjects;

}
