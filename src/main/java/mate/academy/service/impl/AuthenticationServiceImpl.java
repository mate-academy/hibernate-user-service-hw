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

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDbOptional = userService.findByEmail(email);
        if (userFromDbOptional.isPresent()) {
            User user = userFromDbOptional.get();
            String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
            if (user.getPassword().equals(hashedPassword)) {
                System.out.println("User with email=" + email + " logged in successfully!");
                return user;
            }
        }
        throw new AuthenticationException("Incorrect password or login ");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Optional<User> userFromDbOptional = userService.findByEmail(email);
            if (userFromDbOptional.isPresent()) {
                throw new RegistrationException("User with email=" + email
                        + "already registered");
            }
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            System.out.println("User registered successfully!");
            return userService.add(user);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Can't check user with email=" + email + " in DB");
        }
    }
}
