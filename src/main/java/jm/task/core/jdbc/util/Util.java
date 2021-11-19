package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String DATA_BASE_URL = "jdbc:mysql://localhost:3306/new_schema";

    private static final String USER = "root";
    private static final String PASSWORD = "190519805White";

    private static Connection connection;

    public static Connection getConnectionJDBC() {
        try {
            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(DATA_BASE_URL, USER, PASSWORD);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            System.out.println("нет соединения");

        }
        return connection;

    }
}
