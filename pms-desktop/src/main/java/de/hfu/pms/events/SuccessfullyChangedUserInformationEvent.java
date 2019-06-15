package de.hfu.pms.events;

import de.hfu.pms.shared.dto.UserInfoDTO;

public class SuccessfullyChangedUserInformationEvent {

    private final UserInfoDTO userInfoDTO;

    public SuccessfullyChangedUserInformationEvent(UserInfoDTO userInfoDTO) {
        this.userInfoDTO = userInfoDTO;
    }

    public UserInfoDTO getUserInfoDTO() {
        return userInfoDTO;
    }
}
