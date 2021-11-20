package jm.task.core.jdbc;
import jm.task.core.jdbc.service.UserServiceImpl;

//пилим через hiber
public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Ivan", "Polykov", (byte) 7);
        userService.saveUser("Petr", "Yan", (byte) 23);
        userService.saveUser("Konor", "McGregor", (byte) 32);
        userService.removeUserById(1);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();

    }
}
