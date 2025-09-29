package edu.js.project.repository;

import edu.js.project.NewEntities.NewRegulation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NewRegulationRepo extends JpaRepository<NewRegulation,Integer> {

    Optional<NewRegulation> findByRegulationId(String regId);

}
