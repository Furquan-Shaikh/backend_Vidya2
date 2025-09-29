package edu.js.project.dto;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
public class SubjectListDto {

    @Builder.Default
    Map<String,String> subject = new HashMap<>();


}
