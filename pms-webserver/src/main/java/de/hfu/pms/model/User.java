package de.hfu.pms.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table
public class User implements Serializable {

    @Id
    @NotNull
    @Column(unique = true)
    private String username;

    @Column
    @NotNull
    private String password;

    @NotNull
    @Column
    @Enumerated
    private UserRole role;


    /**
     * Default constructor for user. Do not use!
     */
    public User(){
        this.username = null;
        this.password = null;
        this.role = null;
    }

    /**
     * Creates a new user with the specified credentials
     * and normal user rights (no admin rights).
     * @param username the username of the user
     * @param password the password of the user
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = UserRole.USER;
    }

    /**
     * Creates a new user with the specified credentials.
     * @param username the username of the user
     * @param password the password of the user
     * @param role the role of the admin
     */
    public User(@NotNull String username, @NotNull String password, @NotNull UserRole role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String passwordHash) {
        this.password = passwordHash;
    }

    public UserRole getRole() {
        return role;
    }

    public String getRoleAsString(){
        return role.name();
    }

    public void setRole(UserRole role) {
        this.role = role;
    }




}
