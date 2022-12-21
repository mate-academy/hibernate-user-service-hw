package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.model.User;
import mate.academy.service.security.AuthenticationService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) throws RegistrationException, AuthenticationException {
        AuthenticationService authenticationService = (AuthenticationService)
                injector.getInstance(AuthenticationService.class);
        String testEmail = "testEmail@gmail.com";
        String testPassword = "testPassword";
        User registeredUser = authenticationService.register(testEmail, testPassword);
        System.out.println(registeredUser);
        User loginUser = authenticationService.login(testEmail, testPassword);
        System.out.println(loginUser);
    }
}
