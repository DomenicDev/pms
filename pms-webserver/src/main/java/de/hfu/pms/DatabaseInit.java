package de.hfu.pms;

import de.hfu.pms.dao.UserDao;
import de.hfu.pms.model.User;
import de.hfu.pms.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * This service adds some test (sample) entities to the database.
 */
@Service
public class DatabaseInit implements CommandLineRunner {

    private UserDao userDao;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public DatabaseInit(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // add test user with admin rights
        User testAdmin = new User("admin", passwordEncoder.encode("1234"),"test@example.com","Bob","Tester");
        testAdmin.setRole(UserRole.ADMIN);
        userDao.save(testAdmin);
    }
}
