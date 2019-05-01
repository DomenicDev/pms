package de.hfu.pms.events;

public class LoginRequestEvent {

    private final String username;
    private final String password;

    public LoginRequestEvent(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

}
