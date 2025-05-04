package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) throws RegistrationException, AuthenticationException {
        AuthenticationService authenticationService =
                (AuthenticationService) injector.getInstance(AuthenticationService.class);
        UserService userService =
                (UserService) injector.getInstance(UserService.class);

        authenticationService.register("al@gmail.com", "qwer123");
        System.out.println(authenticationService.login("al@gmail.com", "qwer123"));
    }
}
