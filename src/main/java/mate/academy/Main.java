package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;

public class Main {

    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) throws RegistrationException, AuthenticationException {
        final UserService userService = (UserService) injector.getInstance(UserService.class);
        User bob = new User();
        bob.setEmail("bob@bob.com");
        bob.setPassword("qwerty");
        userService.save(bob);

        AuthenticationService authenticationService = (AuthenticationService) injector
                .getInstance(AuthenticationService.class);
        authenticationService.register("bob2@bob.com", "qwerty");
        authenticationService.login("bob2@bob.com", "qwerty");
    }
}
