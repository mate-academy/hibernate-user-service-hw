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
        Optional<User> userFromDbOptional = userService.findByEmail(mail);
        User userFromDb = userFromDbOptional.orElseThrow(
                () -> new AuthenticationException("The mail = " + mail + " didn't find in the DB")
        );
        String passwordFromDb = userFromDb.getPassword();
        String hashedPassword = HashUtil.hashPassword(password, HashUtil.getSalt());
        if (passwordFromDb.equals(hashedPassword)) {
            return userFromDb;
        }
        throw new AuthenticationException("The password " + password + "is wrong.");
    }

    @Override
    public User register(String mail, String password) throws RegistrationException {
        Optional<User> userFromDbOptional = userService.findByEmail(mail);
        if (userFromDbOptional.isPresent()) {
            throw new RegistrationException("The mail = " + mail + " is already registered.");
        }
        User user = new User();
        user.setMail(mail);
        user.setPassword(password);
        userService.add(user);
        return user;
    }
}
