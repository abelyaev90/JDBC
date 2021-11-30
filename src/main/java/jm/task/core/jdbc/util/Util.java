package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;//
//import org.hibernate.boot.registry.StandardServiceRegistryBuilder;//
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;//
import org.hibernate.cfg.Environment;//
import org.hibernate.service.ServiceRegistry;
//import org.hibernate.service.ServiceRegistry;//
import java.sql.*;
import java.util.Properties;//


public class Util {
    // реализуйте настройку соеденения с БД
    private static final String DATA_BASE_URL = "jdbc:mysql://localhost:3306/new_schema";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "190519805White";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static Connection connection;


    public static Connection getConnectionJDBC() {
        try {
            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(DATA_BASE_URL, USER_NAME, PASSWORD);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            System.out.println("нет соединения");

        }
        return connection;

    }

    //реализация подключения с помощью Hibernate

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, DRIVER);
                settings.put(Environment.URL, DATA_BASE_URL);
                settings.put(Environment.USER, USER_NAME);
                settings.put(Environment.PASS, PASSWORD);
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.HBM2DDL_AUTO, "update");

                configuration.setProperties(settings);
                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().
                        applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
