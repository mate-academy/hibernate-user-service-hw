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
        Optional<User> findUser = userService.findByLogin(email);
        if (findUser.isEmpty()) {
            throw new AuthenticationException("Can't authenticate user");
        }
        String hashPassword = HashUtil.hashPassword(password, findUser.get().getSalt());
        if (findUser.get().getPassword().equals(hashPassword)) {
            return findUser.get();
        }
        throw new AuthenticationException("The email or password that you entered is incorrect");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        validateRegisterData(email, password);
        return userService.save(new User(email, password));
    }

    private void validateRegisterData(String email, String password) throws RegistrationException {
        if (email.isEmpty() || password.isEmpty()) {
            throw new RegistrationException("User email or password can't be empty");
        }
        if (userService.findByLogin(email).isPresent()) {
            throw new RegistrationException("User with this email "
                    + email + " is already exist");
        }
    }
}
