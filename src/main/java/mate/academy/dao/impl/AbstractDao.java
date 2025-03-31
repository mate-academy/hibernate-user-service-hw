package mate.academy.dao.impl;

import org.hibernate.SessionFactory;

public class AbstractDao {
    protected final SessionFactory factory;

    protected AbstractDao(SessionFactory sessionFactory) {
        this.factory = sessionFactory;
    }
}
