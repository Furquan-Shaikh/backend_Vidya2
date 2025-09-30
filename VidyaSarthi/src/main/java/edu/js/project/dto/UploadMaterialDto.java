package edu.js.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadMaterialDto {

    private String materialId;
    private String subjectCode;
    private String facultyId;
    private String materialType;
    private String regulationId;
    private MultipartFile pdf;
    private byte[] materialData;
    private String pdfName;



}
