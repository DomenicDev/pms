package de.hfu.pms.service.impl;

import de.hfu.pms.dao.UserDao;
import de.hfu.pms.exceptions.UserNotFoundException;
import de.hfu.pms.model.User;
import de.hfu.pms.model.UserRole;
import de.hfu.pms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(User newUser) {
        for (User user : userDao.findAll()) {
            if (user.getUsername().equals(newUser.getUsername())) {
                throw new org.springframework.dao.DataIntegrityViolationException("User already exists!");
            }
        }
        String password = newUser.getPassword();
        newUser.setPassword(passwordEncoder.encode(password));
        return userDao.save(newUser);
    }

    @Override
    public void deleteUser(String username) {
        for (User user : userDao.findAll()) {
            if (user.getUsername().equals(username)) {
                userDao.delete(user);
                return;
            }
        }
        throw new UserNotFoundException(username);
    }

    @Override
    public User updatePassword(String username, String newPassword) {
        for (User user : userDao.findAll()) {
            if (user.getUsername().equals(username)) {
                user.setPassword(passwordEncoder.encode(newPassword));
                userDao.save(user);
                return user;
            }
        }
        throw new UserNotFoundException(username);
    }

    @Override
    public User updateRole(String username, UserRole role) {
        for (User user : userDao.findAll()) {
            if (user.getUsername().equals(username)) {
                user.setRole(role);
                userDao.save(user);
                return user;
            }
        }
        throw new UserNotFoundException(username);
    }

    @Override
    public User updateEmail(String username, String email) {
        for(User user : userDao.findAll()){
            if(user.getUsername().equals(username)){
                user.setEmail(email);
                userDao.save(user);
                return user;
            }
        }
        throw new UserNotFoundException(username);
    }

    @Override
    public User updateInformation(String username, String forename, String surname, String email) {
        User user = userDao.findByUsername(username);
        if (forename != null) {
            user.setForename(forename);
        }
        if (surname != null) {
            user.setLastname(surname);
        }
        if (email != null) {
            user.setEmail(email);
        }
        return userDao.save(user);
    }

    @Override
    public User getUser(String username) {
        for (User user : userDao.findAll()) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        throw new UserNotFoundException(username);
    }

    @Override
    public List<User> getUserList() {
        return userDao.findAll();
    }
}
