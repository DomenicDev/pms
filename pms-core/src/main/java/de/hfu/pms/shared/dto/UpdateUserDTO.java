package de.hfu.pms.shared.dto;

import de.hfu.pms.shared.enums.UserRole;

public class UpdateUserDTO {

    private String username;
    private String forename;
    private String surname;
    private UserRole role;
    private String email;

    public UpdateUserDTO() {
    }

    public UpdateUserDTO(String username, String forename, String surname, UserRole role, String email) {
        this.username = username;
        this.forename = forename;
        this.surname = surname;
        this.role = role;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
