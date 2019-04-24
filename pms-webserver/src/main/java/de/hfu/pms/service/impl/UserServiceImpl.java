package de.hfu.pms.service.impl;

import de.hfu.pms.dao.UserDao;
import de.hfu.pms.model.User;
import de.hfu.pms.service.UserService;
import de.hfu.pms.shared.dto.UserDto;

import java.util.List;

public class UserServiceImpl implements UserService {

    //@
    private UserDao userDao;

    @Override
    public void createUser(UserDto userDto) {
        User newUser = new User(userDto.getUsername(),userDto.getPassword());
        userDao.save(newUser);
        userDao.flush();
    }

    @Override
    public void deleteUser(UserDto userDto) {
    }
}
