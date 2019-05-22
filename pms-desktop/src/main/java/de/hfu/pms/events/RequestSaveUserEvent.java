package de.hfu.pms.events;

import de.hfu.pms.shared.dto.UserDTO;

public class RequestSaveUserEvent {

    private final UserDTO userDTO;

    public RequestSaveUserEvent(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public UserDTO getUser() {
        return userDTO;
    }
}
