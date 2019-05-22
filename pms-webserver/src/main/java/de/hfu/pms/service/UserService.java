package de.hfu.pms.service;

import de.hfu.pms.model.User;
import de.hfu.pms.model.UserRole;

import java.util.List;

public interface UserService {

    void createUser(User newUser);
    void deleteUser(String username);
    void updatePassword(String username, String oldPassword, String newPassword);
    User updateUserRole(String username, UserRole role);
    User getUser(String username);
    List<User> getUserList();


}
