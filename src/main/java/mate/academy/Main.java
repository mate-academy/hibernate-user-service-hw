package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.model.User;
import mate.academy.security.AuthenticationService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {

        AuthenticationService authenticationService = (AuthenticationService)
                injector.getInstance(AuthenticationService.class);
        String email = "testuser@test.com";
        String password = "test1234";

        try {
            User register = authenticationService.register(email, password);
            System.out.println("The user: " + register.getEmail() + " was add");

            User login = authenticationService.login(email, password);
            System.out.println("User logged");
        } catch (RegistrationException e) {
            throw new RuntimeException("Can't register this user", e);
        } catch (AuthenticationException e) {
            throw new RuntimeException("Can't authenticate this user", e);
        }
    }
}
