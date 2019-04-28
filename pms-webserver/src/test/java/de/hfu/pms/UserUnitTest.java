package de.hfu.pms;

import de.hfu.pms.dao.UserDao;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserUnitTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void checkUniqueConstraintForUser() {
        /*
        User u1 = new User("bob", null);
        User u2 = new User("bob", null);

        userDao.save(u1);
        userDao.save(u2);
*/
    }

}
