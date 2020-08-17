package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;

public class Main {
    private final String testName = "Ivan";
    private final String testLastName = "Ivanov";
    private final byte testAge = 5;

    public static void main(String[] args) {

        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Ivan", "Ivanov", (byte) 5);
        userService.saveUser("Petr", "Ivanovic", (byte) 45);
        userService.saveUser("Alexey", "Ivanovic", (byte) 55);
        userService.saveUser("Alex", "Ivanovic", (byte) 15);
        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
