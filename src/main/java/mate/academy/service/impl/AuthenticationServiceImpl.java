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
    public User login(String email, String password) {
        Optional<User> user = userService.findByEmail(email);
        try {
            isValidPassword(user, password);
            isValidString(email);
            return user.get();
        } catch (AuthenticationException | RegistrationException e) {
            throw new DataProcessingException("Incorrect email or password this user is not exist");
        }
    }

    @Override
    public User register(String email, String password) {
        Optional<User> user = userService.findByEmail(email);
        try {
            isUserPresent(user);
            isValidString(email);
            isValidString(password);
        } catch (RegistrationException e) {
            throw new DataProcessingException(
                    "Incorrect email or password can`t register this user");
        }
        User currentUser = new User();
        currentUser.setEmail(email);
        currentUser.setPassword(password);
        userService.add(currentUser);
        return currentUser;
    }

    private void isValidPassword(Optional<User> user,
                                 String password) throws AuthenticationException {
        isUserExist(user);
        String hashedPassword = HashUtil.hashPassword(password, user.get().getSalt());
        if (!user.get().getPassword().equals(hashedPassword)) {
            throw new AuthenticationException("Incorrect password");
        }
    }

    private void isUserExist(Optional<User> userOptional) throws AuthenticationException {
        if (userOptional.isEmpty()) {
            throw new AuthenticationException("User is not exist");
        }
    }

    private void isUserPresent(Optional<User> userOptional) throws RegistrationException {
        if (userOptional.isPresent()) {
            throw new RegistrationException("User is exist");
        }
    }

    private void isValidString(String str) throws RegistrationException {
        if (str.isEmpty()) {
            throw new RegistrationException("Incorrect email");
        }
    }
}
