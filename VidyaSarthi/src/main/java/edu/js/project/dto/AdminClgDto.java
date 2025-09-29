package edu.js.project.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AdminClgDto {

    private Long id;
    private String adminId;
    private String email;
    private String address;
    private String phone;
    private byte [] imageData;
    private UsersDto usersDto;

}
