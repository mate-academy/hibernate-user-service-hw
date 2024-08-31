package mate.academy.service.impl;

import com.password4j.BcryptFunction;
import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Inject
    private UserService userService;

    public AuthenticationServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> foundUser = userService.findByEmail(email);
        if (foundUser.isEmpty()) {
            throw new AuthenticationException("Email :"
                    + email
                    + "is incorrect, try input the correct one");
        }
        String hash = foundUser.get().getPassword();
        BcryptFunction bcrypt = BcryptFunction.getInstanceFromHash(hash);
        if (bcrypt.hash(password).getResult().equals(hash)) {
            return foundUser.get();
        }
        throw new AuthenticationException("Password: "
                + password
                + " doesn't match, try another one");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> user = userService.findByEmail(email);
        if (user.isPresent()) {
            throw new RegistrationException("Email :" + email + "already exists, try another one");
        }
        User registerUser = new User(email, password);
        return userService.add(registerUser);
    }
}
