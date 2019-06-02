package de.hfu.pms.events;

import de.hfu.pms.shared.dto.UserDTO;

public class RequestChangePasswordEvent {
    private UserDTO userDTO;
    private String newPassword;

    public RequestChangePasswordEvent(UserDTO userDTO, String newPassword){
        this.userDTO = userDTO;
        this.newPassword = newPassword;
    }

    public UserDTO getUser(){
        return userDTO;
    }
    public String getNewPassword(){
        return newPassword;
    }
}
