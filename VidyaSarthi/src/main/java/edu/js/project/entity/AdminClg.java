package edu.js.project.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminClg extends Base{

    @Column(name = "admin_id", nullable = false)
    private String adminId;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false, unique = true)
    private String phone;
    @Lob
    @Column(name = "image_data")
    private byte [] imageData;
    @JsonBackReference("admin-ref")
    @OneToOne(mappedBy = "adminClg")
    private Users users;

}
