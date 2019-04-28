package de.hfu.pms.service;

import de.hfu.pms.model.User;
import de.hfu.pms.model.UserRole;

public interface UserService {

    void createUser(User newUser);
    void deleteUser(String username);
    void updateUserRole(String username, UserRole role);
    User getUser(String username);


}
