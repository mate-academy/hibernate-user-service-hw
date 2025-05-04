package mate.academy.dao.impl;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.UserDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.User;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

@Dao
public class UserDaoImpl implements UserDao {
    @Override
    public User add(User user) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
            return user;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert user " + user, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<User> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(User.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a user by id: " + id, e);
        }
    }

    @Override
    public List<User> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaQuery<User> criteriaQuery = session.getCriteriaBuilder()
                    .createQuery(User.class);
            criteriaQuery.from(User.class);
            return session.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all users", e);
        }
    }

    public Optional<User> findByEmail(String email) {
        /*String query = "FROM User u WHERE u.email = :email";
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        return sessionFactory.getCurrentSession()
                .createQuery(query, User.class)
                .setParameter("email", email)
                .uniqueResultOptional();*/
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        // Створюємо CriteriaBuilder для побудови CriteriaQuery
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

        // Створюємо CriteriaQuery для класу User
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);

        // Створюємо корінь запиту (FROM User)
        Root<User> root = criteriaQuery.from(User.class);

        // Додаємо умову WHERE u.email = :email
        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("email"), email));

        // Виконуємо запит і повертаємо результат як Optional
        return session.createQuery(criteriaQuery).uniqueResultOptional();
    }
}
