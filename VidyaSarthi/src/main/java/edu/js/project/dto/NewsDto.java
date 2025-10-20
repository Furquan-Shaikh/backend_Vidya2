package edu.js.project.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.js.project.NewEntities.NewTeacher;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Builder
@Data
public class NewsDto{

    private int id;
    private String type;
    private String describeEvents;
    private String facultyName;
    private LocalDateTime publishDateTime;



}
