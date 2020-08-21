package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

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
        String createSQLTable = "CREATE TABLE IF NOT EXISTS Users.user1 (id bigint not null auto_increment, age tinyint, lastName varchar(20), name varchar(15), primary key (id))";
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(createSQLTable).executeUpdate();
            transaction.commit();
            System.out.println("Таблица создана с использованием Hibernate");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS Users.user1").executeUpdate();
            transaction.commit();
            System.out.println("Таблица удалена с использованием Hibernate");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User newUser = new User(name, lastName, age);
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(newUser);
            transaction.commit();
            System.out.printf("Пользователь c именем %s добавлен в базу данных с использованием Hibernate\n", name);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User userForDelete = session.get(User.class, id);
            session.delete(userForDelete);
            transaction.commit();
            System.out.printf("Пользователь с id %d был удалён с использованием Hibernate\n", id);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
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
