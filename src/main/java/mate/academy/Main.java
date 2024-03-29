package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Injector;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        @Inject
        AuthenticationService authenticationService
                = (AuthenticationService) injector.getInstance(AuthenticationService.class);

        User user1 = new User("bob", "qwerty");
        User user2 = new User("bob1", "qwerty");
        User user3 = new User("bob", "qwerty");

        try {
            authenticationService.register(user1.getLogin(), user1.getPassword());
            authenticationService.login(user1.getLogin(), user1.getPassword());
        } catch (RegistrationException e) {
            throw new RuntimeException("Cannot register user 1");
        } catch (AuthenticationException e) {
            throw new RuntimeException("Cannot login 1");
        }

        try {
            authenticationService.login(user2.getLogin(), user2.getPassword());
        } catch (AuthenticationException e) {
            System.out.println("Cannot login user 2 because not registered");
        }

        try {
            authenticationService.register(user3.getLogin(), user3.getPassword());
        } catch (RegistrationException e) {
            System.out.println("Cannot register user 3 because same login as user 1");
        }
    }
}
