package edu.js.project.dto;

// package edu.js.project.dto;
public record ResetPasswordRequest(String email, String otp, String newPassword) { }
