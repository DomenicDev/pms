package de.hfu.pms.service;

import de.hfu.pms.model.User;
import de.hfu.pms.model.UserRole;

import java.util.List;

public interface UserService {

    void createUser(User newUser);
    void deleteUser(String username);
    User updatePassword(String username, String newPassword);
    User updateRole(String username, UserRole role);
    User updateEmail(String username, String email);
    User getUser(String username);
    List<User> getUserList();


}
