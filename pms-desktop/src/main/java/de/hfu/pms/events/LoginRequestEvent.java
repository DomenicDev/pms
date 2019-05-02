package de.hfu.pms.events;

public class LoginRequestEvent {

    private final String username;
    private final String pwHash;

    public LoginRequestEvent(String username, String pwHash) {
        this.username = username;
        this.pwHash = pwHash;
    }

    public String getPwHash() {
        return pwHash;
    }

    public String getUsername() {
        return username;
    }

}
