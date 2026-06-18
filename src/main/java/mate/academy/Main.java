package mate.academy;

import mate.academy.lib.Injector;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        AuthenticationService authenticationService =
                (AuthenticationService) injector.getInstance(AuthenticationService.class);

        String login = "alice@gmail.com";
        String password = "alice1234";

        try {
            authenticationService.login(login, password);
        } catch (Exception e) {
            System.out.println("Error while login user. " + e.getMessage());
        }

        try {
            authenticationService.register(login, password);
        } catch (Exception e) {
            System.out.println("Error while registering new user. " + e.getMessage());
        }

        try {
            User alice = authenticationService.login(login, password);
            System.out.println(alice);
        } catch (Exception e) {
            System.out.println("Error while login user. " + e.getMessage());
        }
    }
}
