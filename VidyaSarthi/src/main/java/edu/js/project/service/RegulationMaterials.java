package edu.js.project.service;

import edu.js.project.dto.UploadMaterialDto;
import edu.js.project.dto.UploadNoteDto;

public interface RegulationMaterials {


    void uploadNotes(UploadMaterialDto uploadNoteDto);
    void uploadQb(UploadMaterialDto uploadNoteDto);
    void uploadPyq(UploadMaterialDto uploadNoteDto);

}
