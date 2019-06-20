package de.hfu.pms.events;

import de.hfu.pms.shared.dto.PreviewDoctoralStudentDTO;

public class SuccessfullyUpdatedDoctoralStudentEvent {

    private PreviewDoctoralStudentDTO updatedStudent;

    public SuccessfullyUpdatedDoctoralStudentEvent(PreviewDoctoralStudentDTO updatedStudent) {
        this.updatedStudent = updatedStudent;
    }

    public PreviewDoctoralStudentDTO getUpdatedStudent() {
        return updatedStudent;
    }
}
