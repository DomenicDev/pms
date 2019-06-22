package de.hfu.pms.events;

import de.hfu.pms.shared.dto.UpdateUserDTO;

public class RequestPatchUserEvent {

    private UpdateUserDTO updateUserDTO;

    public RequestPatchUserEvent(UpdateUserDTO updateUserDTO) {
        this.updateUserDTO = updateUserDTO;
    }

    public UpdateUserDTO getUpdateUserDTO() {
        return updateUserDTO;
    }
}
