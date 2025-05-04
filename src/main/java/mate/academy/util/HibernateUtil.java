package mate.academy.util;

import jakarta.persistence.Entity;
import java.util.Set;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.reflections.Reflections;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = initSessionFactory();

    private HibernateUtil() {
    }

    private static SessionFactory initSessionFactory() {
        try {
            Configuration configuration = new Configuration().configure();
            Reflections reflections = new Reflections("mate.academy.model");
            Set<Class<?>> entityClasses = reflections.getTypesAnnotatedWith(Entity.class);
            for (Class<?> entityClass : entityClasses) {
                configuration.addAnnotatedClass(entityClass);
            }
            return configuration.buildSessionFactory();
        } catch (Exception e) {
            throw new RuntimeException("Error creating SessionFactory", e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
