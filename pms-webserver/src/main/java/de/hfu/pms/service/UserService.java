package de.hfu.pms.service;

import de.hfu.pms.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    void createUser();
    void deleteUser();


}
