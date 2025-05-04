package mate.academy.service.impl;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.Optional;
import mate.academy.dao.UserDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;

@Service
public class UserServiceImpl implements UserService {
    @Inject
    private UserDao userDao;

    @Override
    public User add(User user) {
        user.setSalt(HashUtil.getSalt());
        user.setPassword(HashUtil.hashPassword(user.getPassword(), user.getSalt()));
        return userDao.add(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery =
                    cb.createQuery(User.class);
            Root<User> root = criteriaQuery.from(User.class);
            Predicate predicate = cb.equal(root.get("email"), email);
            criteriaQuery.select(root).where(predicate);
            return session.createQuery(criteriaQuery).uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Can't find user with email: "
                    + email + " in database", e);
        }
    }
}
