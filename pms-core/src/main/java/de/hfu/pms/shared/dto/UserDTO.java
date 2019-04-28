package de.hfu.pms.shared.dto;

import de.hfu.pms.shared.enums.UserRole;

public class UserDTO {


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

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    private String username;

    private String password;

    private UserRole userRole;

    public UserDTO() {
    }
}
