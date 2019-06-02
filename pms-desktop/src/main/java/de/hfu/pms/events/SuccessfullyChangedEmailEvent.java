package de.hfu.pms.events;

import de.hfu.pms.shared.dto.UserDTO;

public class SuccessfullyChangedEmailEvent {

    private final UserDTO userDTO;

    public SuccessfullyChangedEmailEvent(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public UserDTO getUser() {
        return userDTO;
    }
}
