package mate.academy.dao.impl;

import mate.academy.dao.AuthenticationDao;
import mate.academy.dao.UserDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.lib.Inject;
import mate.academy.model.User;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;

@Dao
public class AuthenticationDaoImpl implements AuthenticationDao {
    @Inject
    private UserDao userDao;

    @Override
    public User login(String email, String password) {
        User user = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            user = session.get(User.class, email);
            if (user.getPassword().equals(password)) {
                return user;
            }
        } catch (Exception e) {
            throw new DataProcessingException("Can't login user with email " + email, e);
        }
        return user;
    }
}
