package edu.js.project.repository;

import edu.js.project.entity.AdminClg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<AdminClg,Long> {

    Optional<AdminClg> findByAdminId(String adminId);

}
