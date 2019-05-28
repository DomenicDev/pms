package de.hfu.pms.events;

import de.hfu.pms.shared.dto.UserDTO;

public class SuccessfullyChangedUserRoleEvent {

    private final UserDTO userDTO;

    public SuccessfullyChangedUserRoleEvent(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public UserDTO getUser() {
        return userDTO;
    }
}
