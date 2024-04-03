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

        String userName1 = "bob";
        String userName2 = "bob2";
        String password = "qwerty";

        User user1 = new User(userName1, password);
        User user2 = new User(userName2, password);
        User user3 = new User(userName1, password);

        try {
            authenticationService.register(userName1, password);
            authenticationService.login(userName1, password);
        } catch (RegistrationException e) {
            throw new RuntimeException("Cannot register user 1");
        } catch (AuthenticationException e) {
            throw new RuntimeException("Cannot login 1");
        }

        try {
            authenticationService.login(userName2, password);
        } catch (AuthenticationException e) {
            System.out.println("Cannot login user 2 because not registered");
        }

        try {
            authenticationService.register(userName1, password);
        } catch (RegistrationException e) {
            System.out.println("Cannot register user 3 because same login as user 1");
        }
    }
}
