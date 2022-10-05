package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final AuthenticationService authenticationService =
            (AuthenticationService) injector.getInstance(AuthenticationService.class);

    public static void main(String[] args) {
        try {
            User registerUser = authenticationService.register("root", "1234");
            User loginUser = authenticationService.login("root", "1234");
        } catch (RegistrationException | AuthenticationException e) {
            throw new RuntimeException(e);
        }
    }
}
