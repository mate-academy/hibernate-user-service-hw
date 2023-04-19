package mate.academy;

import mate.academy.lib.Injector;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final AuthenticationService authenticationService
            = (AuthenticationService) injector.getInstance(AuthenticationService.class);

    public static void main(String[] args) {
        try {
            User alexUser = authenticationService
                    .register("alexemail@gmail.com", "qwerty");
            User felixUser = authenticationService
                    .register("felixemail@gmail.com", "helloWorld");
            User aliceUser = authenticationService
                    .register("aliceemail@gmail.com", "ukraine1991");
            // AN ADDITIONAL MESSAGE
            System.out.println("I have registered new instances of users");
        } catch (Exception e) {
            throw new RuntimeException("Couldn't register users", e);
        }
        try {
            User authenticatedAlexUser = authenticationService
                    .login("alexemail@gmail.com", "qwerty");
            User authenticatedFelixUser = authenticationService
                    .login("felixemail@gmail.com", "helloWorld");
            User authenticatedAliceUser = authenticationService
                    .login("aliceemail@gmail.com", "ukraine1991");
            System.out.println(authenticatedAlexUser);
            System.out.println(authenticatedFelixUser);
            System.out.println(authenticatedAliceUser);
            // AN ADDITIONAL MESSAGE
            System.out.println("I have printed some authenticated users");
        } catch (Exception e) {
            throw new RuntimeException("Couldn't authorize users", e);
        }
    }
}
