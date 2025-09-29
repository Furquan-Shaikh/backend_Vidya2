package edu.js.project.controller;

import edu.js.project.dto.AddMaterialDto;
import edu.js.project.dto.RegulationInputDto;
import edu.js.project.dto.UnitDto;
import edu.js.project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/VidyaSarthi/addRegulation")
@RequiredArgsConstructor

public class RegulationController {

    private final UserService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createRegulation(@RequestBody RegulationInputDto input) {
        // use input.getRegulation(), input.getSubjectDto(), etc.
        service.addRegulation(input);
        return ResponseEntity.ok("Received " + input.getSubjectDto().size() + " subjects");
    }

    @PostMapping("/addUnit")
    public ResponseEntity<?> createUnit(@RequestBody UnitDto dto){

        service.addUnit(dto);
        return ResponseEntity.ok("Added Successfully");

    }

    @PostMapping("/addMaterial")
    public ResponseEntity<?> addMaterial(@RequestBody AddMaterialDto dto){

        service.addMaterial(dto.getMaterialDto(),dto.getSubCode(),dto.getFacultyId(),dto.getUnitCode());
        return ResponseEntity.ok("Material added in unit");

    }



}
