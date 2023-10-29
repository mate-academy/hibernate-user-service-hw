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
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> useFromDbOptional = userService.findByEmail(email);
        if (useFromDbOptional.isPresent()
                && passwordValidation(password,useFromDbOptional.get())) {
            return useFromDbOptional.get();
        }
        throw new AuthenticationException("Email or login was incorrect. Please, try again!");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (email.isEmpty() || password.isEmpty()) {
            throw new RegistrationException(
                    "Can`t authenticate user, because email are password is empty");
        }

        Optional<User> userByEmail = userService.findByEmail(email);
        if (userByEmail.isPresent()) {
            throw new RegistrationException("User already exist.");
        }
        User user = new User(email, password);
        return userService.add(user);
    }

    private boolean passwordValidation(String password, User userFromDB) {
        String hashPassword = HashUtil.hashPassword(password, userFromDB.getSalt());
        return hashPassword.equals(userFromDB.getPassword());
    }
}
