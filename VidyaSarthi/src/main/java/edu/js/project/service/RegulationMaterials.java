package edu.js.project.service;

import edu.js.project.dto.NewComplainDto;
import edu.js.project.dto.UpdatedStatusDto;
import edu.js.project.dto.UploadMaterialDto;
import edu.js.project.dto.UploadNoteDto;

import java.util.List;

public interface RegulationMaterials {


    void uploadNotes(UploadMaterialDto uploadNoteDto);
    void uploadQb(UploadMaterialDto uploadNoteDto);
    void uploadPyq(UploadMaterialDto uploadNoteDto);

    List<NewComplainDto> getAllComplains(String facultyId);

    void updateComplain(UpdatedStatusDto updatedStatus);
}
