package mate.academy.security;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{
    @Inject
    private UserService userService;

    @Override
    public void register(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        userService.save(user);
    }

    @Override
    public User login(String email, String password) {
        Optional<User> userFromDb = userService.findByEmail(email);
        if (userFromDb.isEmpty()){
            throw new AuthenticationException("Can't find user by login - " + email);
        }
        User user = userFromDb.get();
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (hashedPassword.equals(user.getPassword())) {
            return user;
        }
        throw new AuthenticationException("Wrong password");
    }
}
