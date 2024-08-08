package mate.academy.service.impl;

import mate.academy.dao.UserDao;
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
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new AuthenticationException("Wrong login or password"));

        String hashPassword = HashUtil.getHashOfPassword(password, user.getSalt());

        if (user.getPassword().equals(hashPassword)) {
            return user;
        }

        throw new AuthenticationException("Wrong login or password");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if(userService.findByEmail(email).orElse(null) != null){
            throw new RegistrationException("This email already exists " + email);
        }

        if(password == null){
            throw new RegistrationException("Invalid password value");
        }

        byte[] userSalt = HashUtil.getSalt();
        String hashPassword = HashUtil.getHashOfPassword(password,userSalt);

        User newUser = new User(email,hashPassword,userSalt);

        return userService.add(newUser);
    }
}
