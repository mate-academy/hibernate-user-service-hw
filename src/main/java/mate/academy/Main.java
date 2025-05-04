package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.lib.Injector;
import mate.academy.model.User;
import mate.academy.service.AuthentificationService;

public class Main {
    private static final Injector injector = Injector
            .getInstance("mate.academy");

    public static void main(String[] args) {
        AuthentificationService authentificationService = (AuthentificationService)
                injector.getInstance(AuthentificationService.class);
        try {
            User user = authentificationService.login("ravshan567@gmail.com",
                    "ravshan4ik4irik");
            System.out.println(user);
        } catch (AuthenticationException e) {
            System.err.println("Login failed: " + e.getMessage());
        }
    }
}
