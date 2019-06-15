package de.hfu.pms.shared.dto;

/**
 * This class is used to change the user information of an existing user.
 */
public class ChangeUserInformationDTO {

    private String forename;
    private String lastName;
    private String email;

    public ChangeUserInformationDTO() {
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
