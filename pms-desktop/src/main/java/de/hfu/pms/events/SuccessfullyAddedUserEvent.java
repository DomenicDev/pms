package de.hfu.pms.events;

import de.hfu.pms.shared.dto.UserInfoDTO;

public class SuccessfullyAddedUserEvent {

    private final UserInfoDTO userDTO;

    public SuccessfullyAddedUserEvent(UserInfoDTO userDTO) {
        this.userDTO = userDTO;
    }

    public UserInfoDTO getUser() {
        return userDTO;
    }
}
