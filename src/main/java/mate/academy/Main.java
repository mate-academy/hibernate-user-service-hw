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
    private static final String WRONG_PASSWORD = "wrong";

    public static void main(String[] args) {
        AuthenticationService authenticationService = (AuthenticationService)
                INJECTOR.getInstance(AuthenticationService.class);

        // case of registration with a non-existing email
        try {
            User registeruser = authenticationService.register(TEST_EMAIL, TEST_PASSWORD);
            System.out.println(registeruser);
        } catch (RegistrationException e) {
            throw new RuntimeException(e.getMessage());
        }

        // case of logging in with the correct login and password
        try {
            User userLogin = authenticationService.login(TEST_EMAIL, TEST_PASSWORD);
            System.out.println(userLogin);
        } catch (AuthenticationException e) {
            throw new RuntimeException(e.getMessage());
        }

        // case of registration with existing email
        try {
            authenticationService.register(TEST_EMAIL, TEST_PASSWORD);
        } catch (RegistrationException e) {
            System.out.println(e.getMessage());
        }

        //case of logging in with the wrong password
        try {
            authenticationService.login(TEST_EMAIL, WRONG_PASSWORD);
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());
        }
    }
}
