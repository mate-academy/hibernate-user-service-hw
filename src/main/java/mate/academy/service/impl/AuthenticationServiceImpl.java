package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
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
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String hashedPass = HashUtil.hashPass(password, user.getSalt());
            if (hashedPass.equals(user.getPassword())) {
                return user;
            }
        }
        throw new AuthenticationException(
                "There is no user with such email or password. Try again");
    }

    @Override
    public User register(String mail, String password) {
        User user = new User();
        user.setEmail(mail);
        user.setPassword(password);
        return userService.add(user);
    }
}
