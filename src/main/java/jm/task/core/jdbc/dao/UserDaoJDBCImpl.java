package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (id BIGINT PRIMARY KEY AUTO_INCREMENT," +
                " name VARCHAR (20), lastname VARCHAR (20), age TINYINT)";
        try (Connection connection = Util.getConnectionJDBC();
             Statement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate(sql);
            System.out.println("Таблица создана");
        } catch (SQLException throwable) {
            System.out.println("Создание таблицы не удалось");
        }

    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";
        try (Connection connection = Util.getConnectionJDBC();
             Statement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate(sql);
            System.out.println("Таблица удалена");
        } catch (SQLException throwable) {
            System.out.println("Удаление таблицы не удалось");
        }

    }

    //Добавление User в таблицу
    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users(name, lastname, age) VALUES (?,?,?)";
        try (Connection connection = Util.getConnectionJDBC()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException throwable) {
            System.out.println("Юзеры не добавлены");
        }
    }

    //Удаление User из таблицы ( по id )
    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection connection = Util.getConnectionJDBC();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("User с id – " + id + " удален из базы данных");
        } catch (SQLException throwable) {
            System.out.println("Удаление юзера не удалось");
        }
    }

    //Получение всех User(ов) из таблицы
    //прописать URL
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection connection = Util.getConnectionJDBC();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {

                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("lastname"));
                user.setAge(rs.getByte("age"));
                System.out.println(user);
                userList.add(user);
            }
            System.out.println("получили всех user из таблицы");
        } catch (SQLException throwable) {
            System.out.println("получение не удалось");
        }
        return userList;
    }

    //Очистка содержания таблицы
    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE users";
        try (Connection connection = Util.getConnectionJDBC();
             Statement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate(sql);
            System.out.println("Таблица очищена");
        } catch (SQLException throwable) {
            System.out.println("Очистка таблицы не удалась");
        }
    }
}
