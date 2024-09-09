package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.model.User;
import mate.academy.security.AuthenticationService;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");
    private static final String TEST_EMAIL = "test@gmail.com";
    private static final String TEST_PASSWORD = "test";

    public static void main(String[] args) {
        AuthenticationService authenticationService = (AuthenticationService)
                INJECTOR.getInstance(AuthenticationService.class);

        // case of registration with a non-existing email
        try {
            User registeredUser = authenticationService.register(TEST_EMAIL, TEST_PASSWORD);
            System.out.println(registeredUser);
        } catch (RegistrationException e) {
            throw new RuntimeException(e.getMessage());
        }

        // case of logging in with the correct login and password
        try {
            User user = authenticationService.login(TEST_EMAIL, TEST_PASSWORD);
            System.out.println(user);
        } catch (AuthenticationException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
