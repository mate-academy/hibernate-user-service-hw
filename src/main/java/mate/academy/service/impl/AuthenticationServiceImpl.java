package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDB = userService.findByEmail(email);
        if (userFromDB.isEmpty()) {
            throw new AuthenticationException("Incorrect login or password");
        }
        User user = userFromDB.get();
        String hashPassword = HashUtil.hashPassword(password, user.getSalt());
        if (user.getPassword().equals(hashPassword)) {
            return user;
        }
        throw new AuthenticationException("Incorrect login or password");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> getUserByEmailQuery
                    = session.createQuery("from User where email=:email", User.class);
            getUserByEmailQuery.setParameter("email", email);
            if (getUserByEmailQuery.uniqueResultOptional().isPresent()) {
                throw new RegistrationException(
                        "Your email is already registered. Email: " + email);
            }
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        userService.add(user);
        return user;
    }
}
