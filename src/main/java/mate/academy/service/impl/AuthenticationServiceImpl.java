package mate.academy.service.impl;

import mate.academy.exception.AuthenticationException;
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
        User user = userService.findByEmail(email).orElse(new User());
        if (user.getEmail() != null) {
            boolean userExist = user.getEmail().equals(email);
            boolean passwordIsCorrect = HashUtil.hashPassword(password, user.getSalt())
                    .equals(user.getPassword());

            if (userExist && passwordIsCorrect) {
                System.out.println("User: " + user.getEmail() + " was successfully logged in.");
                return user;
            }
        }
        throw new AuthenticationException("Can`t log in user: "
                + user.getEmail() + ". Login or password are incorrect");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        User user = new User();
        user.setEmail(email);
        user.setSalt(HashUtil.getSalt());
        user.setPassword(HashUtil.hashPassword(password, user.getSalt()));

        try {
            return userService.add(user);
        } catch (Exception e) {
            throw new RegistrationException("Can`t register user with email: " + email, e);
        }
    }
}
