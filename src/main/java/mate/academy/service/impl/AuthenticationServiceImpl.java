package mate.academy.service.impl;

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
        Optional<User> existingUserOptional = userService.findByEmail(email);
        if (!existingUserOptional.isEmpty()
                && existingUserOptional.get().getPassword().equals(password)) {
            return existingUserOptional.get();
        }
        throw new AuthenticationException("Given email or password for: "
                + email + " are incorrect.");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (!userService.findByEmail(email).isEmpty()) {
            throw new RegistrationException("User with given email: " + email + " already exist.");
        }
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(password);
        userService.add(newUser);
        return newUser;
    }
}
