package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final static String CREATE_TABLE = """
            CREATE TABLE IF NOT EXISTS `1_1_3_ichurs`.`users` (
                `id` INT NOT NULL AUTO_INCREMENT,
                `name` VARCHAR(45) NULL DEFAULT NULL,
                `lastname` VARCHAR(45) NULL DEFAULT NULL,
                `age` INT NULL DEFAULT NULL,
                PRIMARY KEY (`id`),
                UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);""";
    private final static String ADD_USER = "INSERT INTO users(name, lastname, age) VALUES(?, ?, ?)";
    private final static String GET_ALL_USERS = "SELECT * FROM users";
    private final static String REMOVE_BY_ID = "DELETE FROM users WHERE id = ?";
    private final static String CLEAN_TABLE = "TRUNCATE TABLE users";
    private final static String DROP_TABLE = "DROP TABLE IF EXISTS users";
    private PreparedStatement preparedStatement;

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable(){
        try (Connection connection = Util.getConnection()) {
            preparedStatement = connection.prepareStatement(CREATE_TABLE);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection()) {
            preparedStatement = connection.prepareStatement(DROP_TABLE);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        try (Connection connection = Util.getConnection()) {
            preparedStatement = connection.prepareStatement(ADD_USER);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setByte(3, user.getAge());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection()) {
            preparedStatement = connection.prepareStatement(REMOVE_BY_ID);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_ALL_USERS);
            while (resultSet.next()) {
                User user = new User();
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for (User u: userList) {
            System.out.println(u.toString());
        }
        return userList;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection()) {
            preparedStatement = connection.prepareStatement(CLEAN_TABLE);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
