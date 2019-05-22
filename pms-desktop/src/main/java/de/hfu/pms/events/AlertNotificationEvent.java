package de.hfu.pms.events;

/**
 * This event type is used to show messages to the user,
 * e.g. by using an prompt dialog.
 */
public class AlertNotificationEvent {

    public static final int INFO = 1;
    public static final int WARNING = 2;
    public static final int ERROR = 3;

    private String message;
    private int type;

    /**
     * Creates a new notification event using the specified messages's content.
     * @param type the type of the notifications (see global constants)
     * @param message the message to be shown on the screen
     */
    public AlertNotificationEvent(int type, String message) {
        this.type = type;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public int getType() {
        return type;
    }
}
