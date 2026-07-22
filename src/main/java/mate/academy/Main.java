package mate.academy;

import mate.academy.lib.Injector;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

public class Main {

    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        final UserService userService = (UserService) injector.getInstance(UserService.class);
        User bob = new User();
        bob.setEmail("bob@bob.com");
        bob.setSalt(HashUtil.getSalt());
        bob.setPassword(HashUtil.hashPassword("qwerty", bob.getSalt()));
        userService.save(bob);

        AuthenticationService authenticationService = (AuthenticationService) injector
                .getInstance(AuthenticationService.class);
        authenticationService.login("bob@bob.com", "qwerty");
        authenticationService.register("bob2@bob.com", "qwerty");
    }
}
