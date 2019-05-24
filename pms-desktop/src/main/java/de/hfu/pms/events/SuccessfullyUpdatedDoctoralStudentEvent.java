package de.hfu.pms.events;

import de.hfu.pms.shared.dto.DoctoralStudentDTO;

public class SuccessfullyUpdatedDoctoralStudentEvent {

    private DoctoralStudentDTO updatedStudent;

    public SuccessfullyUpdatedDoctoralStudentEvent(DoctoralStudentDTO updatedStudent) {
        this.updatedStudent = updatedStudent;
    }

    public DoctoralStudentDTO getUpdatedStudent() {
        return updatedStudent;
    }
}
