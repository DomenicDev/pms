package de.hfu.pms.events;


public class RequestChangeUserInformationEvent {

    private String username;
    private String forename;
    private String surname;
    private String email;

    public RequestChangeUserInformationEvent(String username, String forename, String surname, String email) {
        this.username = username;
        this.forename = forename;
        this.surname = surname;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getForename() {
        return forename;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }
}
