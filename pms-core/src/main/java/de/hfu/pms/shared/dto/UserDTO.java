package de.hfu.pms.shared.dto;

import de.hfu.pms.shared.enums.UserRole;

public class UserDTO {

    private String username;

    private String password;

    private String forename;

    private String lastname;

    private String email;

    private UserRole userRole;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public UserDTO(){}

    public UserDTO(String username, String password, String forename, String lastname, String email, UserRole userRole) {
        this.username = username;
        this.password = password;
        this.forename = forename;
        this.lastname = lastname;
        this.email = email;
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", forename='" + forename + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", userRole=" + userRole +
                '}';
    }
}
