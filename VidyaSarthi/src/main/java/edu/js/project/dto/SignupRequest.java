package edu.js.project.dto;

import java.util.Set;

public record SignupRequest (String username, String password, Set<String> roles){
}
