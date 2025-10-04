package edu.js.project.responseStructure;

public record LoginSuccess(String token, String role, boolean success, String email, String userId) {
}
