package de.hfu.pms.events;

import de.hfu.pms.shared.dto.DoctoralStudentDTO;

public class SaveDoctoralStudentEvent {

    private final DoctoralStudentDTO doctoralStudent;

    public SaveDoctoralStudentEvent(DoctoralStudentDTO doctoralStudent) {
        this.doctoralStudent = doctoralStudent;
    }

    public DoctoralStudentDTO getDoctoralStudent() {
        return doctoralStudent;
    }
}
