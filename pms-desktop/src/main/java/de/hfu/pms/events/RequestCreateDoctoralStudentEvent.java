package de.hfu.pms.events;

import de.hfu.pms.shared.dto.CreateDoctoralStudentDTO;

public class RequestCreateDoctoralStudentEvent {

    private CreateDoctoralStudentDTO createDoctoralStudentDTO;

    public RequestCreateDoctoralStudentEvent(CreateDoctoralStudentDTO createDoctoralStudentDTO) {
        this.createDoctoralStudentDTO = createDoctoralStudentDTO;
    }

    public CreateDoctoralStudentDTO getCreateDoctoralStudentDTO() {
        return createDoctoralStudentDTO;
    }
}
