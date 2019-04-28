package de.hfu.pms.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(unique = true)
    private String username;

    //sha-256 Hashed Password
    @Column
    private String password;

    //Administrator or normal User
    @Column
    @Enumerated
    private UserRole role;


    //Costructors
    public User(){
        this.username = "";
        this.password = "";
        this.role = UserRole.user;
    }
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = UserRole.user;
    }


    //Getter and Setter
    public Long getId() {
        return id;
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




}
