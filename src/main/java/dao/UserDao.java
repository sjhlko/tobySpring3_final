package dao;

import domain.User;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.*;
import java.util.Map;

public class UserDao {
    private ConnectionMaker connectionMaker;
    public  UserDao(){
        this.connectionMaker = new AwsConnectionMaker();
    }
    public  UserDao(ConnectionMaker connectionMaker){
        this.connectionMaker = connectionMaker;
    }

    public void jdbcContextWithStatementStrategy(StatementStrategy stmt){
        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = connectionMaker.makeConnection();
            ps = stmt.makePreparedStatement(c);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if(ps!=null){
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if(c!=null){
                try {
                    c.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void deleteAll() throws SQLException{
        jdbcContextWithStatementStrategy(new DeleteAllStrategy());

    }

    public int getCount() throws SQLException{
        ResultSet rs = null;
        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = connectionMaker.makeConnection();
            ps = c.prepareStatement("select count(*) from users");
            rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e){
            throw new RuntimeException(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

    public void add(User user) {
        AddStrategy addStrategy = new AddStrategy(user);
        jdbcContextWithStatementStrategy(addStrategy);
    }

    public User findById(String id) {
        try {
            // DB접속 (ex sql workbeanch실행)
            Connection c = connectionMaker.makeConnection();

            // Query문 작성
            PreparedStatement pstmt = c.prepareStatement("SELECT * FROM users WHERE id = ?");
            pstmt.setString(1, id);

            // Query문 실행
            ResultSet rs = pstmt.executeQuery();
            User user = null;
            if(rs.next()){
                user = new User(rs.getString("id"), rs.getString("name"),
                        rs.getString("password"));

            }
            rs.close();
            pstmt.close();
            c.close();
            if(user == null) throw new EmptyResultDataAccessException(1);

            return user;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        UserDao userDao = new UserDao();
        User user = userDao.findById("1");
        System.out.println(user.getName());
    }
}
