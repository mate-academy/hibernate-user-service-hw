package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.service.impl.AuthenticationServiceImpl;

public class Main {
    private static Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        UserService userService = (UserService) injector.getInstance(UserService.class);

        AuthenticationService authenticationService = new AuthenticationServiceImpl(userService);
        try {
            System.out.println(authenticationService.register("bob@gmail.com", "bobpassword"));
            System.out.println(authenticationService.login("bob@gmail.com", "bobpassword"));
            System.out.println(authenticationService.login("bob@gmail", "bobpassword"));
        } catch (RegistrationException e) {
            throw new RuntimeException("Can't register user", e);
        } catch (AuthenticationException e) {
            throw new RuntimeException("Can't login user", e);
        }

    }
}
