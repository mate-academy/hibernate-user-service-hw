package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        AuthenticationService authenticationService =
                (AuthenticationService) injector.getInstance(AuthenticationService.class);

        try {
            User registerUser = authenticationService.register("oleg", "2604");
            System.out.println(registerUser);
        } catch (RegistrationException e) {
            System.out.println(e.getMessage());
        }
        try {
            User loginUser = authenticationService.login("oleg", "2604");
            System.out.println(loginUser);
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
    }
}
