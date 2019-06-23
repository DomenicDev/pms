package de.hfu.pms.events;

public class LoginFailedEvent {

    private String reason;

    public LoginFailedEvent(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
}
