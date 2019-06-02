package de.hfu.pms.events;

import de.hfu.pms.shared.dto.UserDTO;

public class RequestChangeEmailEvent {

    private final UserDTO userDTO;
    String newEmail;

    public RequestChangeEmailEvent(UserDTO userDTO) {
        this.userDTO = userDTO;
        this.newEmail =newEmail;
    }

    public UserDTO getUser() {
        return userDTO;
    }
    public String getNewEmail(){
        return newEmail;
    }
}
