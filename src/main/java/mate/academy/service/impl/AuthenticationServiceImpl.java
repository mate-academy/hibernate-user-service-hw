package mate.academy.service.impl;

import java.util.Optional;
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
    @Inject
    private UserDao userDao;

    // Метод для логіну користувача
    @Override
    public User login(String email, String password) throws AuthenticationException {
        // 1. Знаходимо користувача за email
        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new AuthenticationException("Invalid email or password!");
        }
        User user = userOptional.get();

        // 2. Якщо користувача нема або пароль не збігається — кидаємо AuthenticationException
        String hashedInputPassword = HashUtil.hashPassword(password, user.getSalt());
        if (!hashedInputPassword.equals(user.getPassword())) {
            throw new AuthenticationException("Invalid email or password!");
        }
        // 3. Якщо все добре — повертаємо користувача
        return userOptional.get();
    }

    // Метод для реєстрації нового користувача
    @Override
    public User register(String email, String password) throws RegistrationException {
        // 1. Перевірка: чи вже існує користувач з таким email
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("E-mail "
                    + email + " is already registered!");
        }

        // 2. Створюємо нового користувача
        User user = new User();
        user.setEmail(email);
        /* Hashing and salt generation are handled inside UserService.add(),
        following SRP and keeping password processing logic encapsulated.*/
        user.setPassword(password); // Зберігаємо сирий пароль — UserService сам його захешує

        try {
            // 3. Додаємо користувача через UserService (там буде хешування + сіль)
            return userService.add(user);
        } catch (Exception e) {
            throw new RegistrationException("Registration failed: ", e);
        }
    }
}
