package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.lib.Inject;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;
    
    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> optionalUser = userService.findByEmail(email);
        User user = optionalUser.get();
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (optionalUser.isEmpty() || !user.getPassword().equals(hashedPassword)) {
            throw new AuthenticationException("User or password is incorrect, please enter valid "
                    + "data.");
        }
        return user;
    }
    
    @Override
    public User register(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setSalt(HashUtil.getSalt());
        user.setPassword(HashUtil.hashPassword(password, user.getSalt()));
        return userService.add(user);
    }
}
