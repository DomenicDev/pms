package de.hfu.pms.service;

import de.hfu.pms.dao.UserDao;
import de.hfu.pms.model.UserRole;
import de.hfu.pms.shared.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    void createUser(UserDto userDto);
    void deleteUser(String username);
    void updateUserRole(String username, UserRole role);


}
