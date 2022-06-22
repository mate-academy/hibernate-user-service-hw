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
        User validUser;
        try {
            validUser = authenticationService
                    .register("validEmail@gmail.com", "validPassword");
        } catch (RegistrationException e) {
            throw new RuntimeException("Can't register user", e);
        }

        try {
            System.out.println(authenticationService
                    .login(validUser.getEmail(), validUser.getPassword()));
        } catch (AuthenticationException e) {
            throw new RuntimeException("Can't login user", e);
        }
    }
}
