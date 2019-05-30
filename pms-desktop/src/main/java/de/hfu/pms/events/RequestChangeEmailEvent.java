package de.hfu.pms.events;

import de.hfu.pms.shared.dto.UserDTO;

public class RequestChangeEmailEvent {

    private final UserDTO userDTO;

    public RequestChangeEmailEvent(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public UserDTO getUser() {
        return userDTO;
    }
}
