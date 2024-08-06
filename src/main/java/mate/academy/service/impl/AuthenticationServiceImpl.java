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
        Optional<User> userByEmailOptional = userService.findByEmail(email);
        User user = null;
        String hashedPassword = null;
        if (userByEmailOptional.isPresent()) {
            user = userByEmailOptional.get();
            hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        }
        if (user == null || !user.getPassword().equals(hashedPassword)) {
            throw new AuthenticationException("Can't authenticate user with email: "
                    + email);
        }
        return user;
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        validateEmail(email);
        validatePassword(password);
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.add(user);
    }

    private void validatePassword(String password) throws RegistrationException {
        if (password == null || password.isEmpty()) {
            throw new RegistrationException("Can't register user with null or empty password");
        }
    }

    private void validateEmail(String email) throws RegistrationException {
        if (email == null || email.isEmpty()) {
            throw new RegistrationException("Can't register user with null or empty email");
        }
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> queryByEmail = session.createQuery(
                    "from User u where u.email = :email", User.class);
            queryByEmail.setParameter("email", email);
            User userByEmail = queryByEmail.uniqueResult();
            if (userByEmail != null) {
                throw new RegistrationException("Can't register user. "
                        + "User with such email already exists");
            }
        }
    }
}
