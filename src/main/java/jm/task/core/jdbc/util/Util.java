package jm.task.core.jdbc.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private final String url1 = "jdbc:mysql://localhost:3306/Users?serverTimezone=UTC";
    private final String userName = "root";
    private final String password = "vhyrx4fsg";
    private static SessionFactory factory;

    public Util() {
    }

//    static {
//        factory = new Configuration()
//                .configure()
//                .buildSessionFactory();
//    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url1, userName, password);
    }

    public Connection getTransactedConnection() throws SQLException {
        final Connection connection1 = DriverManager.getConnection(url1, userName, password);
        connection1.setAutoCommit(false);
        connection1.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        return connection1;
    }

    public static SessionFactory getSessionFactory() {
        if (factory == null) {
            factory = new Configuration()
                    .configure()
                    .buildSessionFactory();
        }
        return factory;
    }
}
