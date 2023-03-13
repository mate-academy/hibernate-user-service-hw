package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    private static UserService userService =
            (UserService) injector.getInstance(UserService.class);
    private static AuthenticationService authenticationService =
            (AuthenticationService) injector.getInstance(AuthenticationService.class);

    public static void main(String[] args) {
        User user = new User();
        user.setSalt(HashUtil.getSalt());
        user.setEmail("email@gmail.com");
        user.setPassword(HashUtil.hashPassword("qweety1", user.getSalt()));
        System.out.println(userService.add(user));
        System.out.println(userService.findByEmail(user.getEmail()));

        try {
            authenticationService.register("email@bobik", "sobaka");
            authenticationService.login("email@bobik", "sobaka");
        } catch (AuthenticationException | RegistrationException e) {
            throw new RuntimeException(e);
        }
    }
}
