package de.hfu.pms.events;

import de.hfu.pms.shared.dto.PatchDoctoralStudentDTO;

public class RequestPatchDoctoralStudentEvent {

    private PatchDoctoralStudentDTO patchDoctoralStudentDTO;

    public RequestPatchDoctoralStudentEvent(PatchDoctoralStudentDTO patchDoctoralStudentDTO) {
        this.patchDoctoralStudentDTO = patchDoctoralStudentDTO;
    }

    public PatchDoctoralStudentDTO getPatchDoctoralStudentDTO() {
        return patchDoctoralStudentDTO;
    }
}
