package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (id BIGINT PRIMARY KEY AUTO_INCREMENT," +
                " name VARCHAR (20), lastname VARCHAR (20), age TINYINT)";
        try (Connection connection = Util.getConnectionJDBC();
             Statement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate(sql);
            System.out.println("Таблица создана hiber");
        } catch (SQLException throwable) {
            System.out.println("Создание таблицы не удалось hiber");
        }

    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";
        try (Connection connection = Util.getConnectionJDBC();
             Statement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate(sql);
            System.out.println("Таблица удалена hiber");
        } catch (SQLException throwable) {
            System.out.println("Удаление таблицы не удалось hiber");
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            user.setName(name);
            user.setLastName(lastName);
            user.setAge(age);

            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }


    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        try (Session session = Util.getSessionFactory().openSession()) {

            Query query = session.createQuery("FROM User");
            userList = query.getResultList();

        } catch (Exception e) {
            System.out.println("получение юзеров не сработало");
        }
        return userList;
    }



    @Override
    public void cleanUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Query queryObj = session.createQuery("DELETE FROM User");
        queryObj.executeUpdate();
        transaction.commit();
        session.close();
    }
}
