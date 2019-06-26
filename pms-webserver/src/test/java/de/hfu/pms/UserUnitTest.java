package de.hfu.pms;

import de.hfu.pms.dao.UserDao;
import de.hfu.pms.exceptions.UserNotFoundException;
import de.hfu.pms.model.User;
import de.hfu.pms.model.UserRole;
import de.hfu.pms.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserUnitTest {



    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;


    @Test
    public void checkUniqueConstraintForUser() {

        User user = new User();
        user.setUsername("bob");
        user.setForename("Bob");
        user.setLastname("Baumeiser");
        user.setEmail("bob@example.com");
        user.setPassword("12345");
        user.setRole(UserRole.ADMIN);

        User user2 = new User();
        user2.setUsername("bob2");
        user2.setForename("Bob2");
        user2.setLastname("Baumeiser");
        user2.setEmail("bob@example.com");
        user2.setPassword("12345");
        user2.setRole(UserRole.ADMIN);

        userService.createUser(user);
        userService.createUser(user2);

        assertThatThrownBy(() -> userService.createUser(user)).isInstanceOf(org.springframework.dao.DataIntegrityViolationException.class);
    }

    @Test
    public void checkDeleteUser(){
        String username = "bob";

        User user = new User();
        user.setUsername(username);
        user.setForename("Bob");
        user.setLastname("Baumeiser");
        user.setEmail("bob@example.com");
        user.setPassword("12345");
        user.setRole(UserRole.ADMIN);

        userService.createUser(user);
        assertEquals(userService.getUser(username).getUsername(),username);
        userService.deleteUser(username);
        assertThatThrownBy(() -> userService.getUser(username)).isInstanceOf(UserNotFoundException.class);

    }

}
