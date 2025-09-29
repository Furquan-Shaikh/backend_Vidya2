package edu.js.project.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsersDto {

    public UsersDto(String email, String password, String roles) {
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    private Long id;
    private String email;
    private String password;
    private String roles;
    private AdminClgDto adminClgDto;
    private TeacherDto teacherDto;
    private StudentDto studentDto;

}
