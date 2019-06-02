package de.hfu.pms.events;

import de.hfu.pms.shared.dto.UserDTO;

public class RequestAddUserEvent {

    private final UserDTO userDTO;

    public RequestAddUserEvent(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public UserDTO getUser() {
        return userDTO;
    }
}
