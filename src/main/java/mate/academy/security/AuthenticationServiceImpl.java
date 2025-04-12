package mate.academy.security;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.DataProcessingException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromOptional = userService.findByEmail(email);
        if (userFromOptional.isEmpty()) {
            throw new AuthenticationException("Invalid email or password");
        }
        User user = userFromOptional.get();
        String hashPassword = HashUtil.hashPassword(password,user.getSalt());
        if (user.getPassword().equals(hashPassword)) {
            return user;
        }
        throw new AuthenticationException("Invalid email or password");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        User user = new User();
        try {
            Optional<User> newUser = userService.findByEmail(email);
            if (newUser.isPresent()) {
                throw new RegistrationException("User already exists");
            }
            transaction = session.beginTransaction();
            user.setEmail(email);
            byte[] salt = HashUtil.getSalt();
            String hashPassword = HashUtil.hashPassword(password,salt);
            user.setPassword(hashPassword);
            user.setSalt(salt);
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Cannot register user by email" + email, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return user;
    }
}
