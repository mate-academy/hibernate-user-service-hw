package mate.academy;

import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;

public class Main {
    public static void main(String[] args) throws Exception {
        Injector injector = Injector.getInstance("mate.academy");

        UserService userService = (UserService) injector.getInstance(UserService.class);

        userService.add(new User("blah", "blah"));
        System.out.println("Add success");

        userService.findByEmail("blah");
        System.out.println("Find by email success");

        String email = "bohdanzorii@mate.academy";
        String password = "very_safe_password007";

        AuthenticationService authenticationService =
                (AuthenticationService) injector.getInstance(AuthenticationService.class);

        authenticationService.register(email, password);
        System.out.println("Register success");

        authenticationService.login(email, password);
        System.out.println("Login success");

        try {
            authenticationService.register(email, password);
        } catch (RegistrationException e) {
            System.out.println("Duplicate email register denied - success");
        }
    }
}
