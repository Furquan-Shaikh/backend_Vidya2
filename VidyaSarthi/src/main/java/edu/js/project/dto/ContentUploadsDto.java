package edu.js.project.dto;

import edu.js.project.enums.MaterialType;
import lombok.Builder;
import lombok.Data;
import java.util.Map;

@Data
@Builder
public class ContentUploadsDto {
    private long totalUploads;
    private Map<MaterialType, Long> uploadsByType;
}