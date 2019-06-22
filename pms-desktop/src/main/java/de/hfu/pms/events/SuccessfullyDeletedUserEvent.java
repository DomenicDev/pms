package de.hfu.pms.events;

public class SuccessfullyDeletedUserEvent {

    private String username;

    public SuccessfullyDeletedUserEvent(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
