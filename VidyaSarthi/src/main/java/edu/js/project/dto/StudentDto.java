package edu.js.project.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String studentId;
    private String password;
    private String address;
    private String branch;
    private String year;
    private String regulation;
    private byte[] imageData;
    private UsersDto usersDto;
    private String semester;

}
