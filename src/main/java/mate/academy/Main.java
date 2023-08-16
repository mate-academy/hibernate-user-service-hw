package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        AuthenticationService authenticationService
                = (AuthenticationService) injector.getInstance(AuthenticationService.class);

        User bob = new User("bob@gmail.com", "qwerty");
        User alice = new User("null", "null");

        try {
            User userRegistered = authenticationService.register(bob.getEmail(), bob.getPassword());
            System.out.println(userRegistered);
            userRegistered = authenticationService.register(alice.getEmail(), alice.getPassword());
            System.out.println(userRegistered);
        } catch (RegistrationException e) {
            throw new RuntimeException(e);
        }

        try {
            User userEntered = authenticationService.login(null, alice.getPassword());
            System.out.println(userEntered);
            userEntered = authenticationService.login(bob.getEmail(), bob.getPassword());
            System.out.println(userEntered);
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
    }
}
