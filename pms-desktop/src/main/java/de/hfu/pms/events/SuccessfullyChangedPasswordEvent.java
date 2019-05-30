package de.hfu.pms.events;

import de.hfu.pms.shared.dto.UserDTO;

public class SuccessfullyChangedPasswordEvent {
    private UserDTO userDTO;
    public SuccessfullyChangedPasswordEvent(UserDTO userDTO){
        if (userDTO == null) {
            throw new IllegalArgumentException("null not allowed");
        }
        this.userDTO = userDTO;
    }

    public UserDTO getUser(){
        return userDTO;
    }
}
