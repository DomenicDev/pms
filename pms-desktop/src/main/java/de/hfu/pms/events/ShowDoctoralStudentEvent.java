package de.hfu.pms.events;

import de.hfu.pms.shared.dto.DoctoralStudentDTO;

/**
 * This shows asks to show the specified doctoral student in the gui (form mask).
 */
public class ShowDoctoralStudentEvent {

    private DoctoralStudentDTO doctoralStudentDTO;

    public ShowDoctoralStudentEvent(DoctoralStudentDTO doctoralStudentDTO) {
        this.doctoralStudentDTO = doctoralStudentDTO;
    }

    public DoctoralStudentDTO getDoctoralStudentDTO() {
        return doctoralStudentDTO;
    }
}
