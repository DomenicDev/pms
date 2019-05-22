package de.hfu.pms.events;

import de.hfu.pms.shared.dto.DoctoralStudentDTO;

public class SuccessfullyAddedDoctoralStudentEvent {

    private DoctoralStudentDTO doctoralStudentDTO;

    public SuccessfullyAddedDoctoralStudentEvent(DoctoralStudentDTO doctoralStudentDTO) {
        this.doctoralStudentDTO = doctoralStudentDTO;
    }

    public DoctoralStudentDTO getDoctoralStudentDTO() {
        return doctoralStudentDTO;
    }
}
