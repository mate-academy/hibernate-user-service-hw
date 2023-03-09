package mate.academy.security.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.security.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> optionalUserFromDB = userService.findByEmail(email);
        if (optionalUserFromDB.isEmpty() || !optionalUserFromDB.get().getPassword()
                .equals(HashUtil.hashPassword(password, optionalUserFromDB.get().getSalt()))) {
            throw new AuthenticationException("Can't login user with email " + email + ". This user"
                    + " not exists or password is not valid");
        }
        return optionalUserFromDB.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("Can't register user with email " + email
                    + ". This user" + " is already exists in DB");
        }
        User user = new User();
        user.setEmail(email);
        user.setSalt(HashUtil.getSalt());
        user.setPassword(HashUtil.hashPassword(password, user.getSalt()));
        userService.add(user);
        return user;
    }
}
