package de.hfu.pms.events;

import de.hfu.pms.shared.dto.UserDTO;

public class SuccessfullyChangedPasswordEvent {
    private String response;
    public SuccessfullyChangedPasswordEvent(String response){
        this.response = response;
    }

    public String getResponse(){
        return response;
    }
}
