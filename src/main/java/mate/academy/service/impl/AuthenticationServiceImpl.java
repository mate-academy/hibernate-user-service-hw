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

    public AuthenticationServiceImpl() {
    }

    public AuthenticationServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public User login(String login, String password) throws AuthenticationException {
        Optional<User> userByLogin = userService.findByLogin(login);
        if (userByLogin.isEmpty()) {
            throw new AuthenticationException("Can not authenticate user");
        }
        User user = userByLogin.get();
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());

        if (user.getPassword().equals(hashedPassword)) {
            return user;
        } else {
            throw new AuthenticationException("Can not authenticate user");
        }
    }

    @Override
    public User register(String login, String password) {
// 2. Згенерувати унікальну сіль для нового пароля
        byte[] saltBytes = HashUtil.getSalt();
//        String saltString = HashUtil.bytesToString(saltBytes); // Перетворити сіль на String для збереження в БД

        // 3. Захешувати пароль, використовуючи згенеровану сіль
        String hashedPassword = HashUtil.hashPassword(password, saltBytes);

        // 4. Створити об'єкт User, передавши email, ЗАХЕШОВАНИЙ ПАРОЛЬ та СІЛЬ
        // Переконайтеся, що ваш конструктор класу User може приймати ці 3 параметри
        User user = new User(login, hashedPassword);

        // 5. Зберегти об'єкт User у базі даних за допомогою userService
        return userService.save(user); // Або userService.save(user), якщо у вас такий метод
    }
}
