package de.hfu.pms.service.impl;

import de.hfu.pms.dao.UserDao;
import de.hfu.pms.service.UserService;
import de.hfu.pms.shared.dto.UserDto;

public class UserServiceImpl implements UserService {

    private UserDao userDao;

    @Override
    public void createUser(UserDto userDto) {
        userDao.
    }

    @Override
    public void deleteUser(UserDto userDto) {
        userDao.delete(userDao);
    }
}
