package edu.js.project.dto;

import edu.js.project.entity.Material;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TeacherDto {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String facultyId;
    private String designation;
    private String subject;
    private String password;
    private String address;
    private byte[] imageData;
    private UsersDto usersDto;
    private Set<MaterialDto> materialDto;


}
