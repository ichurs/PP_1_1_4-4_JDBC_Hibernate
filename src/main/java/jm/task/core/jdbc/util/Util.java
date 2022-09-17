package jm.task.core.jdbc.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/1_1_3_ichurs";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static StandardServiceRegistry standardServiceRegistry;
    private static SessionFactory sessionFactory;

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    static {
        StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();
        Map<String, String> dbSettings = new HashMap<>();
        dbSettings.put(Environment.URL, "jdbc:mysql://localhost:3306/1_1_3_ichurs");
        dbSettings.put(Environment.USER, "root");
        dbSettings.put(Environment.PASS, "root");
        dbSettings.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
        dbSettings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
        registryBuilder.applySettings(dbSettings);
        standardServiceRegistry = registryBuilder.build();
        MetadataSources sources = new MetadataSources(standardServiceRegistry);
        Metadata metadata = sources.getMetadataBuilder().build();
        sessionFactory = metadata.getSessionFactoryBuilder().build();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
