package de.hfu.pms.service;

import de.hfu.pms.model.UserRole;
import de.hfu.pms.shared.dto.UserDto;

public interface UserService {

    void createUser(UserDto userDto);
    void deleteUser(String username);
    void updateUserRole(String username, UserRole role);


}
