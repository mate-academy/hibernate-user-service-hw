package mate.academy.security;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String mail, String password) throws AuthenticationException {
        Optional<User> userFromDbOptional = userService.findByMail(mail);
        if (userFromDbOptional.isEmpty()) {
            throw new AuthenticationException("User by this mail not registered ");
        }
        User user = userFromDbOptional.get();
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (user.getPassword().equals(hashedPassword)) {
            return user;
        }
        throw new AuthenticationException("Wrong password ");
    }

    @Override

    public User register(String mail, String password) throws RegistrationException {
        Optional<User> userFromDbOptional = userService.findByMail(mail);
        if (userFromDbOptional.isPresent()) {
            throw new RegistrationException("Unable to register, this email is already in use "
                    + mail);
        }
        User user = new User();
        user.setMail(mail);
        user.setPassword(password);
        return userService.add(user);
    }
}
