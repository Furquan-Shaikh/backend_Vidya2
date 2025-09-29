package edu.js.project.responseStructure;

import java.util.Set;

public record SignupRequest (String username, String password, Set<String> roles){
}
