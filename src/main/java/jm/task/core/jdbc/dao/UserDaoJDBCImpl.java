package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoJDBCImpl implements UserDao {
    private final static String CREATE_TABLE = """
            CREATE TABLE IF NOT EXISTS `1_1_3_ichurs`.`users` (
                `id` BIGINT(19) NOT NULL AUTO_INCREMENT,
                `name` VARCHAR(45) NULL DEFAULT NULL,
                `lastname` VARCHAR(45) NULL DEFAULT NULL,
                `age` INT NULL DEFAULT NULL,
                PRIMARY KEY (`id`),
                UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);""";
    private final static String ADD_USER = "INSERT INTO users(name, lastname, age) VALUES(?, ?, ?)";
    private final static String GET_ALL_USERS = "SELECT id, name, lastname, age FROM users";
    private final static String REMOVE_BY_ID = "DELETE FROM users WHERE id = ?";
    private final static String CLEAN_TABLE = "TRUNCATE TABLE users";
    private final static String DROP_TABLE = "DROP TABLE IF EXISTS users";
    private PreparedStatement preparedStatement;
    Logger logger = Logger.getLogger(UserDaoJDBCImpl.class.getName());

    public UserDaoJDBCImpl() {
        }

    public void createUsersTable(){
        try (Connection connection = Util.getConnection()) {
            preparedStatement = connection.prepareStatement(CREATE_TABLE);
            preparedStatement.executeUpdate();
            connection.setAutoCommit(false);
            try {
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Failed create table");
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection()) {
            preparedStatement = connection.prepareStatement(DROP_TABLE);
            preparedStatement.executeUpdate();
            connection.setAutoCommit(false);
            try {
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Failed drop table");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection()) {
            preparedStatement = connection.prepareStatement(ADD_USER);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            connection.setAutoCommit(false);
            try {
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Failed save user");
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection()) {
            preparedStatement = connection.prepareStatement(REMOVE_BY_ID);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.setAutoCommit(false);
            try {
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Failed remove user");
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_ALL_USERS);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
            }
            connection.setAutoCommit(false);
            try {
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Failed get list of users");
        }
        return userList;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection()) {
            preparedStatement = connection.prepareStatement(CLEAN_TABLE);
            preparedStatement.executeUpdate();
            connection.setAutoCommit(false);
            try {
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Failed clean table");
        }
    }
}
