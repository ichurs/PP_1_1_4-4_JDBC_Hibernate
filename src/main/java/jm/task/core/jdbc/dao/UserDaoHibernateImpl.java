package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
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

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {

    }

    @Override
    public void dropUsersTable() {

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

    }

    @Override
    public void removeUserById(long id) {

    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public void cleanUsersTable() {

    }
}
