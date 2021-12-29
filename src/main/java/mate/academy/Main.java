package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.service.AuthenticationService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final AuthenticationService authenticationService = (AuthenticationService)
            injector.getInstance(AuthenticationService.class);

    public static void main(String[] args) {
        try {
            authenticationService.register("gordon@gmail.com", "ambas");
        } catch (RegistrationException e) {
            throw new RuntimeException("Can`t register new user", e);
        }

        try {
            System.out.println(authenticationService.login("gordon@gmail.com", "ambas"));
        } catch (AuthenticationException e) {
            throw new RuntimeException("Can`t login", e);
        }
    }
}
