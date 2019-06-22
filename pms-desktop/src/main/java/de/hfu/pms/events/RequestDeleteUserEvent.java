package de.hfu.pms.events;

public class RequestDeleteUserEvent {

    private String username;

    public RequestDeleteUserEvent(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
