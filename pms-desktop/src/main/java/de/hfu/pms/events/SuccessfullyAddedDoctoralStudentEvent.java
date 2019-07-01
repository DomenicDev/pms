package de.hfu.pms.events;

import de.hfu.pms.shared.dto.PreviewDoctoralStudentDTO;

public class SuccessfullyAddedDoctoralStudentEvent {

    private PreviewDoctoralStudentDTO doctoralStudentDTO;

    public SuccessfullyAddedDoctoralStudentEvent(PreviewDoctoralStudentDTO doctoralStudentDTO) {
        this.doctoralStudentDTO = doctoralStudentDTO;
    }

    public PreviewDoctoralStudentDTO getDoctoralStudentDTO() {
        return doctoralStudentDTO;
    }
}
