package de.hfu.pms.events;

public class RequestChangePasswordEvent {

    private String username;
    private String newPassword;

    public RequestChangePasswordEvent(String username, String newPassword){
        this.username = username;
        this.newPassword = newPassword;
    }

    public String getUsername(){
        return username;
    }
    public String getNewPassword(){
        return newPassword;
    }
}
