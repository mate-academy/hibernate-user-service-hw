package mate.academy.dao.impl;

import java.util.function.Consumer;
import java.util.function.Function;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

@Dao
public class AbstractDao<T> {
    protected final SessionFactory factory;

    public AbstractDao(SessionFactory factory) {
        this.factory = factory;
    }

    protected <T> T executeTransaction(Function<Session, T> function) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            T result = function.apply(session);
            transaction.commit();
            return result;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Transaction failed", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    protected void executeTransaction(Consumer<Session> consumer) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            consumer.accept(session);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Transaction failed", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
