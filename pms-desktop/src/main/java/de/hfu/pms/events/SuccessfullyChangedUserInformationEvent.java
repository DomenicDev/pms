package de.hfu.pms.events;

import de.hfu.pms.shared.dto.UserDTO;

public class SuccessfullyChangedUserInformationEvent {

    private final UserDTO userDTO;

    public SuccessfullyChangedUserInformationEvent(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }
}
