package de.hfu.pms.events;

import de.hfu.pms.shared.dto.UserDTO;

public class RequestChangeUserInformationEvent {

    private UserDTO userDTO;
    private String newForename;
    private String newLastname;
    private String newEmail;

    public RequestChangeUserInformationEvent(UserDTO user) {
        this.userDTO = user;
        this.newEmail =newEmail;
        this.newForename =newForename;
        this.newLastname =newLastname;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }
    public String getNewEmail(){
        return newEmail;
    }
    public String getNewForename(){
        return newForename;
    }
    public String getNewLastname(){
        return newLastname;
    }
}
