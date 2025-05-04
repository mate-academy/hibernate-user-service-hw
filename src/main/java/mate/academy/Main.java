package mate.academy;

import mate.academy.lib.Injector;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final AuthenticationService authenticationService =
            (AuthenticationService) injector.getInstance(AuthenticationService.class);
    private static final UserService userService =
            (UserService) injector.getInstance(UserService.class);

    public static void main(String[] args) {
        User registeredUser = authenticationService.register("test@example.com", "password123");
        System.out.println("User registered: " + registeredUser);

        User loggedInUser1 = authenticationService.login("test@example.com", "password123");
        System.out.println("User logged in: " + loggedInUser1);
    }
}
