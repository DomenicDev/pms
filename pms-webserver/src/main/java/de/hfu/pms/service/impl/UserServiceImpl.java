package de.hfu.pms.service.impl;

import de.hfu.pms.dao.UserDao;
import de.hfu.pms.model.User;
import de.hfu.pms.model.UserRole;
import de.hfu.pms.service.UserService;
import de.hfu.pms.shared.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void createUser(User newUser) {
        userDao.save(newUser);
    }

    @Override
    public void deleteUser(String username) {
        for(User user : userDao.findAll()){
            if(user.getUsername().equals(username)){
                userDao.delete(user);
            }
        }

    }

    @Override
    public void updateUserRole(String username, UserRole role) {
        for(User user :userDao.findAll()){
            if(user.getUsername().equals(username)){
                user.setRole(role);
            }
        }
    }
}
