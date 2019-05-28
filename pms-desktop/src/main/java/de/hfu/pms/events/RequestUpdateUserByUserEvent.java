package de.hfu.pms.events;

import de.hfu.pms.shared.dto.UserDTO;

public class RequestUpdateUserByUserEvent {

    private UserDTO userDTO;

    public RequestUpdateUserByUserEvent(UserDTO userDTO){
        this.userDTO = userDTO;
    }

    public UserDTO getUser(){
        return userDTO;
    }
}
