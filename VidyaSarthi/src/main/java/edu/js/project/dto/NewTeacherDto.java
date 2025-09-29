package edu.js.project.dto;

import edu.js.project.NewEntities.NewMaterial;
import edu.js.project.NewEntities.NewSubject;
import edu.js.project.entity.Base;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;
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
