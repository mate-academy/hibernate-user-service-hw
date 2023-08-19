package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final String BOB_EMAIL = "bob@mail.com";
    private static final String BOB_PASSWORD = "bob123";
    private static final String ALICE_EMAIL = "alice@mail.com";
    private static final String ALICE_PASSWORD = "alice123";

    public static void main(String[] args) {
        AuthenticationService authenticationService = (AuthenticationService)
                injector.getInstance(AuthenticationService.class);
        UserService userService = (UserService) injector.getInstance(UserService.class);
        try {
            authenticationService.register(BOB_EMAIL, BOB_PASSWORD);
        } catch (RegistrationException e) {
            System.out.println("Can't register user with: " + BOB_EMAIL + " - NOT OK");
        }

        System.out.println(userService.findByEmail(BOB_EMAIL).get()
                + " found by email " + BOB_EMAIL);
        try {
            userService.findByEmail(ALICE_EMAIL);
        } catch (Exception e) {
            System.out.println("Can't find user by email not present in db - OK");
        }

        try {
            authenticationService.register(ALICE_EMAIL, ALICE_PASSWORD);
        } catch (RegistrationException e) {
            System.out.println("Can't register user with " + ALICE_EMAIL + " - NOT OK");
        }
        try {
            authenticationService.login(ALICE_EMAIL, ALICE_PASSWORD);
        } catch (AuthenticationException e) {
            System.out.println("Can't login with "
                    + ALICE_EMAIL + " - NOT OK");
        }

        try {
            authenticationService.login(BOB_EMAIL, "565");
        } catch (AuthenticationException e) {
            System.out.println("Can't login with "
                     + BOB_EMAIL + " with wrong password" + " - OK");
        }

        try {
            authenticationService.register(BOB_EMAIL, BOB_PASSWORD);
        } catch (Exception e) {
            System.out.println("Can't register user with: " + BOB_EMAIL
                    + ", email already registered - OK");
        }
        try {
            authenticationService.login(ALICE_EMAIL, "wrongPassword");
        } catch (AuthenticationException e) {
            System.out.println("Can't login with "
                    + ALICE_EMAIL + " and wrong password, after registration - OK");
        }
    }
}
