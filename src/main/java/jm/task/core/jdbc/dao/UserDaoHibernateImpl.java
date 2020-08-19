package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory;


    public UserDaoHibernateImpl() {
        sessionFactory = Util.getSessionFactory();
    }


    @Override
    public void createUsersTable() {
        String createSQLTable = "CREATE TABLE IF NOT EXISTS Users.user (id bigint not null auto_increment, age tinyint, lastName varchar(20), name varchar(15), primary key (id))";
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            session.createSQLQuery(createSQLTable).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица создана с использованием Hibernate");
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            session.createSQLQuery("DROP TABLE IF EXISTS Users.user").executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица удалена с использованием Hibernate");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User newUser = new User(name, lastName, age);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(newUser);
            session.getTransaction().commit();
            System.out.printf("Пользователь c именем %s добавлен в базу данных с использованием Hibernate\n", name);
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            User userForDelete = session.get(User.class, id);
            session.delete(userForDelete);
            session.getTransaction().commit();
            System.out.printf("Пользователь с id %d был удалён с использованием Hibernate\n", id);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> authorList;
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> userCriteriaQuery = builder.createQuery(User.class);
            userCriteriaQuery.from(User.class);
            authorList = session.createQuery(userCriteriaQuery).getResultList();
        }
        return authorList;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            session.createQuery("DELETE FROM User").executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица очищена с использованием Hibernate");
        }
    }
}
