package edu.js.project.repository;

import edu.js.project.entity.Regulation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegulationRepository extends JpaRepository<Regulation,Integer> {
}
