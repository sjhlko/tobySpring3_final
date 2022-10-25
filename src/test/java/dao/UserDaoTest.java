package dao;

import domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {
    @Test
    void addAndSelect() throws SQLException, ClassNotFoundException {
        UserDao userDao = new UserDaoFactory().awsUserDao();
        User user = new User ("345","hi","1234");
        userDao.add(user);
        User selectedUser = userDao.findById("345");
        Assertions.assertEquals("hi",selectedUser.getName());
    }

}