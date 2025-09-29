package edu.js.project.responseStructure;

import edu.js.project.dto.UsersDto;
import lombok.Data;

@Data
public class LoginResultResponse {


    String token;
    boolean success;
    UsersDto dto;

}
