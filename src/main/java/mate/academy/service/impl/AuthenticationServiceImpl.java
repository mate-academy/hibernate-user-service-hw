package mate.academy.service.impl;

import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;
import mate.academy.util.HibernateUtil;

import javax.naming.AuthenticationException;
import java.util.Objects;
import java.util.Optional;

import static mate.academy.util.HashUtil.hashPassword;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userByEmailOptional = userService.findByEmail(email);
        if (userByEmailOptional.isEmpty()) {
            throw new AuthenticationException("User with email: " + email + " don't registered");
        }
        if(password.isEmpty()) {
            throw new AuthenticationException("This password length is 0 or null");
        }
        if (Objects.equals(userByEmailOptional.get().getPassword(),
                hashPassword(password, userByEmailOptional.get().getSalt()))) {
            return userByEmailOptional.get();
        }
        throw new AuthenticationException("Not valid password");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> userByEmailOptional = userService.findByEmail(email);
        if (userByEmailOptional.isPresent()) {
            throw new RegistrationException("User with email: " + email + " is registered");
        }
        if(password.isEmpty()) {
            throw new RegistrationException("This password length is 0");
        }

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setSalt(HashUtil.getSalt());
        newUser.setPassword(hashPassword(password, newUser.getSalt()));

        return userService.add(newUser);
    }
}
