package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegisterException;
import mate.academy.lib.Injector;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        System.out.println("********User register********");
        AuthenticationService authenticationService = (AuthenticationService)
                injector.getInstance(AuthenticationService.class);
        try {
            User registerUser1 = authenticationService
                    .register("pietroGabinski@gmail.com", "p4S#31");
            System.out.println(registerUser1);
        } catch (RegisterException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("********User login********");
        try {
            User loginUser1 = authenticationService
                    .login("pietroGabinski@gmail.com", "p4S#31");
            System.out.println(loginUser1);
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());
        }
    }
}
