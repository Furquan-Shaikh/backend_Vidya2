package edu.js.project.dto;

import lombok.Data;

import java.util.List;

@Data
public class FacultyFilterDto {
    private List<String> designation;
    private List<String> subject;
}