package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.model.User;
import mate.academy.security.AuthenticationService;

public class Main {
    private static final Injector inject = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        AuthenticationService authenticationService =
                (AuthenticationService) inject.getInstance(AuthenticationService.class);
        User successfulUser = null;
        User failUser = new User("skrypnikbod", "PASSWORD");
        try {
            successfulUser = authenticationService.register("skrypnikbod@gmail.com", "PASSWORD");
            System.out.println("Successful register user - " + successfulUser);
            failUser = authenticationService.register(failUser.getEmail(), failUser.getPassword());
        } catch (RegistrationException e) {
            System.out.println("Can't register this user - " + failUser
                    + ". Error message: " + e.getMessage());
        }
        failUser.setEmail("skrypnikbod@gmail.com");
        try {
            authenticationService.register(failUser.getEmail(), failUser.getPassword());
        } catch (RegistrationException e) {
            System.out.println("Can't register this user - " + failUser
                    + ". Error message: " + e.getMessage());
        }

        User loginUser = null;
        try {
            loginUser = authenticationService.login("skrypnikbod@gmail.com", "PASSWORD");
            System.out.println("Successful login user - " + loginUser);
            authenticationService.login("skrypnikbod@gmail.com", "PASS");
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());
        }
    }
}
