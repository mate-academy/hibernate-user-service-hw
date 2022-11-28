package mate.academy.dao.impl;

import java.util.Optional;
import mate.academy.dao.AbstractDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public abstract class AbstractDaoImpl<T> implements AbstractDao<T> {
    @Override
    public T add(T object) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(object);
            transaction.commit();
            return object;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException(
                    String.format("Can't insert %s ", object.getClass()), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public abstract Optional<T> get(Long id);
}

