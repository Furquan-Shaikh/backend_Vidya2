package edu.js.project.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class UploadNotesDto {


    private List<SubjectDto> subjectDto = new ArrayList<>();
    private Set<Integer> unitSet = new HashSet<>();

}
