package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.DataProcessingException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> fetched = userService.findByEmail(email);

        User retrieved = fetched.orElseThrow(
                () -> new AuthenticationException("No user found for email " + email));

        if (HashUtil.hashPassword(password, retrieved.getSalt()).equals(retrieved.getPassword())) {
            return retrieved;
        }

        throw new AuthenticationException(
                "Y0u bEtTeR wIpE Y0Ur (0_0) gLaSseS beFoRe logging in with that password :-0");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (password.isBlank() || email.isBlank()) {
            throw new RegistrationException("Either your password or your email is empty or blank");
        }
        try {
            return userService.add(new User(
                    email, password));
        } catch (DataProcessingException e) {
            throw new RegistrationException(String.format(
                    "Cannot register user by email %s and password %s", email, password), e);
        }
    }
}
