package dao;

import domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory.class)
class UserDaoTest {

    @Autowired
    ApplicationContext context;
    @Test
    void addAndSelect() throws SQLException, ClassNotFoundException {
        UserDao userDao = context.getBean("awsUserDao",UserDao.class);
        userDao.deleteAll();
        assertEquals(0,userDao.getCount());
        User user = new User ("3445","hi","1234");
        userDao.add(user);
        assertEquals(1,userDao.getCount());
        User selectedUser = userDao.findById("3445");
        Assertions.assertEquals("hi",selectedUser.getName());
    }


}