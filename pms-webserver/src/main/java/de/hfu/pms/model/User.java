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

    @Column
    @NotNull
    private String forename;

    @Column
    @NotNull
    private String lastname;

    @Column
    @NotNull
    private String email;

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
        this.forename = null;
        this.lastname = null;
        this.email = null;
        this.role = null;
    }

    /**
     * Creates a new user with the specified credentials
     * and normal user rights (no admin rights).
     * @param username the username of the user
     * @param password the password of the user
     * @param email the email of the user
     * @param forename the forename of the user
     * @param lastname the lastname of the user
     */
    public User(@NotNull String username, @NotNull String password,  @NotNull String forename, @NotNull String lastname, @NotNull String email ) {
        this.username = username;
        this.password = password;
        this.forename = forename;
        this.lastname = lastname;
        this.email = email;
        this.role = UserRole.USER;
    }

    /**
     * Creates a new user with the specified credentials
     * and normal user rights (no admin rights).
     * @param username the username of the user
     * @param password the password of the user
     * @param email the email of the user
     * @param forename the forename of the user
     * @param lastname the lastname of the user
     * @param role the role of the user
     */
    public User(@NotNull String username, @NotNull String password, @NotNull String forename, @NotNull String lastname, @NotNull String email, @NotNull UserRole role) {
        this.username = username;
        this.password = password;
        this.forename = forename;
        this.lastname = lastname;
        this.email = email;
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

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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




}
