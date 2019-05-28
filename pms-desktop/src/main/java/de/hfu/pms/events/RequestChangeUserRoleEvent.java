package de.hfu.pms.events;

import de.hfu.pms.shared.dto.UserDTO;

public class RequestChangeUserRoleEvent {

    private final UserDTO userDTO;

    public RequestChangeUserRoleEvent(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public UserDTO getUser() {
        return userDTO;
    }
}
