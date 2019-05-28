package de.hfu.pms.events;

import de.hfu.pms.shared.dto.UserDTO;

public class SuccessfullyAddedUserEvent {

    private final UserDTO userDTO;

    public SuccessfullyAddedUserEvent(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public UserDTO getUser() {
        return userDTO;
    }
}
