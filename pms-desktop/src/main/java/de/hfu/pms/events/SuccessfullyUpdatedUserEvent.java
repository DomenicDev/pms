package de.hfu.pms.events;

import de.hfu.pms.shared.dto.UserInfoDTO;

public class SuccessfullyUpdatedUserEvent {

    private final UserInfoDTO userInfoDTO;

    public SuccessfullyUpdatedUserEvent(UserInfoDTO userInfoDTO) {
        this.userInfoDTO = userInfoDTO;
    }

    public UserInfoDTO getUserInfoDTO() {
        return userInfoDTO;
    }
}
