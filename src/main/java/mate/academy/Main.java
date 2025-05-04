package mate.academy;

import mate.academy.lib.Injector;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        AuthenticationService authenticationService =
                (AuthenticationService) INJECTOR.getInstance(AuthenticationService.class);
        User newUser = authenticationService.register("email@gmail.com", "correct pass");

        System.out.println(authenticationService.login(newUser.getEmail(), "correct pass"));
        authenticationService.login(newUser.getEmail(), "wrong pass");
    }
}
