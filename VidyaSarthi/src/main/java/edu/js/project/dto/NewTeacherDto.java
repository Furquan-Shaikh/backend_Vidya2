package edu.js.project.dto;

import edu.js.project.NewEntities.NewMaterial;
import edu.js.project.NewEntities.NewSubject;
import lombok.Builder;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Builder

@Getter
public class NewTeacherDto {

    private String name;
    private String email;
    private String phone;
    private String facultyId;
    private String designation;
    private String password;
    private String address;
    private byte[] imageData;
    private Set<NewSubject> subjects = new HashSet<>();
    private Set<NewMaterial> materials = new HashSet<>();
    private Set<String> subjectCodes = new HashSet<>();


}
