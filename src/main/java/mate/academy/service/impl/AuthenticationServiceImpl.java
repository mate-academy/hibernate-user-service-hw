package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.EmailAlreadyExistsException;
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
        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isEmpty() || !userOptional.get().getPassword()
                .equals(HashUtil.hashPassword(password, userOptional.get().getSalt()))) {
            throw new AuthenticationException("Can't login user with email: " + email);
        }
        return userOptional.get();
    }

    @Override
    public User register(String email, String password) throws EmailAlreadyExistsException {
        if (userService.findByEmail(email).isPresent()) {
            throw new EmailAlreadyExistsException("email already exists in database");
        }
        User user = new User(email, password);
        return userService.add(user);
    }
}
