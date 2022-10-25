package dao;

import domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory.class)
class UserDaoTest {

    @Autowired
    ApplicationContext context;
    UserDao userDao;
    User user1;
    User user2;
    User user3;

    @BeforeEach
    void setUp(){
        this.userDao = context.getBean("awsUserDao", UserDao.class);
        user1 = new User("1","서정희", "1234");
        user2 = new User("2","서정희2", "1234");
        user3 = new User("3","서정희3", "1234");

    }

    @Test
    void addAndSelect() throws SQLException, ClassNotFoundException {
        userDao.deleteAll();
        assertEquals(0,userDao.getCount());
        userDao.add(user1);
        assertEquals(1,userDao.getCount());
        User selectedUser = userDao.findById(user1.getId());
        Assertions.assertEquals(user1.getName(),selectedUser.getName());
    }

    @Test
    void count() throws SQLException{
        userDao.deleteAll();
        assertEquals(0,userDao.getCount());
        userDao.add(user1);
        assertEquals(1,userDao.getCount());
        userDao.add(user2);
        assertEquals(2,userDao.getCount());
        userDao.add(user3);
        assertEquals(3,userDao.getCount());
    }

    @Test
    void getAllTest(){
        userDao.deleteAll();
        List<User> users = userDao.getAll();
        assertEquals(0,users.size());
        userDao.add(user1);
        userDao.add(user2);
        userDao.add(user3);
        users = userDao.getAll();
        assertEquals(3,users.size());
    }


}