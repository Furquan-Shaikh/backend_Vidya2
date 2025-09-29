package edu.js.project.dto;

import lombok.Data;

import java.util.List;
@Data
public class StudentFilterDto {
    private List<String> semesters;
    private List<Integer> years;
    private List<String> branches;
}