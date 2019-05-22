package de.hfu.pms.events;

import de.hfu.pms.shared.dto.UserDTO;

public class SucessfullyAddedUserEvent {

    private final UserDTO userDTO;

    public SucessfullyAddedUserEvent(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public UserDTO getUser() {
        return userDTO;
    }
}
