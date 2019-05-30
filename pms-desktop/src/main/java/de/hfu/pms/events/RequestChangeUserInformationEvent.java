package de.hfu.pms.events;

import de.hfu.pms.shared.dto.UserDTO;

public class RequestChangeUserInformationEvent {

    private UserDTO userDTO;

    public RequestChangeUserInformationEvent(UserDTO user) {
        this.userDTO = user;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }
}
