package de.hfu.pms.service.impl;

import de.hfu.pms.dao.UserDao;
import de.hfu.pms.exceptions.WrongPasswordException;
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
    public void createUser(User newUser) {
        for(User user : userDao.findAll()){
            if(user.getUsername().equals(newUser.getUsername())){
                throw new org.springframework.dao.DataIntegrityViolationException("User already exists!");
            }
        }
        String password = newUser.getPassword();
        newUser.setPassword(passwordEncoder.encode(password));
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
    public void updatePassword(String username, String oldPassword, String newPassword) {
        String encodedNewPassword = passwordEncoder.encode(newPassword);
        for(User user : userDao.findAll()){
            if(user.getUsername().equals(username)){
                if(passwordEncoder.matches(oldPassword, user.getPassword())){
                    user.setPassword(encodedNewPassword);
                    userDao.save(user);
                }
                else throw new WrongPasswordException();
            }
        }
    }

    @Override
    public void updateUserRole(String username, UserRole role) {
        for(User user :userDao.findAll()){
            if(user.getUsername().equals(username)){
                user.setRole(role);
                userDao.save(user);
            }
        }
    }


    @Override
    public User getUser(String username){
        for(User user :userDao.findAll()){
            if(user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }

    @Override
    public List<User> getUserList() {
        return userDao.findAll();
    }
}
