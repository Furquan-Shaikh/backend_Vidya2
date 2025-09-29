package edu.js.project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadNoteDto {

    private String subjectCode;
    private int unitNo;
    private String url;
    private String facultyId;
    private String title;
}
