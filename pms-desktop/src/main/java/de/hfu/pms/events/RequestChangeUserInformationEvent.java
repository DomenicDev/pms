package de.hfu.pms.events;

import de.hfu.pms.shared.dto.UserInfoDTO;

public class RequestChangeUserInformationEvent {

    private UserInfoDTO userDTO;
    private String newForename;
    private String newLastname;
    private String newEmail;

    public RequestChangeUserInformationEvent(UserInfoDTO user) {
        this.userDTO = user;
        this.newEmail =newEmail;
        this.newForename =newForename;
        this.newLastname =newLastname;
    }

    public UserInfoDTO getUserDTO() {
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
