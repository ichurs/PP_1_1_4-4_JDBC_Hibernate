package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoHibernateImpl implements UserDao {
    private final static String CREATE_TABLE = """
            CREATE TABLE IF NOT EXISTS `1_1_3_ichurs`.`users` (
                `id` BIGINT(19) NOT NULL AUTO_INCREMENT,
                `name` VARCHAR(45) NULL DEFAULT NULL,
                `lastname` VARCHAR(45) NULL DEFAULT NULL,
                `age` INT NULL DEFAULT NULL,
                PRIMARY KEY (`id`),
                UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);""";
    private final static String DROP_TABLE = "DROP TABLE IF EXISTS users";
    private final SessionFactory sessionFactory;
    private Session session = null;
    Logger logger = Logger.getLogger(UserDaoJDBCImpl.class.getName());

    public UserDaoHibernateImpl() {
        sessionFactory = Util.getSessionFactory();
    }

    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.createSQLQuery(CREATE_TABLE).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            logger.log(Level.WARNING, "Failed create table");
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.createSQLQuery(DROP_TABLE).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            logger.log(Level.WARNING, "Failed drop table");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.log(Level.WARNING, "Failed save user");
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            logger.log(Level.WARNING, "Failed remove user");
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = null;
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            userList = session.createQuery("from User", User.class).getResultList();
            session.getTransaction().commit();
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            return userList;
        } catch (Exception e) {
            logger.log(Level.WARNING, "Failed get list of users");
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            session.getTransaction().commit();
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "Failed clean table");
        }
    }
}
