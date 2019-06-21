package de.hfu.pms.events;


public class RequestChangeEmailEvent {

    private final String username;
    private final String newEmail;

    public RequestChangeEmailEvent(String username, String newEmail) {
        this.username = username;
        this.newEmail = newEmail;
    }

    public String getUsername() {
        return username;
    }
    public String getNewEmail(){
        return newEmail;
    }
}
