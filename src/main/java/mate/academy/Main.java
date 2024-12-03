package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) throws RegistrationException, AuthenticationException {

        // 0. INJECTING
        final var userService
                = (UserService) injector.getInstance(UserService.class);
        final var authenticationService
                = (AuthenticationService) injector.getInstance(AuthenticationService.class);

        // 1. CREATING
        // User
        User user1 = new User();
        user1.setEmail("user1@gmail.com");
        user1.setPassword("password");

        User user2 = new User();
        user2.setEmail("user2@gmail.com");
        user2.setPassword("password");

        User user3 = new User();
        user3.setEmail("user3@gmail.com");
        user3.setPassword("password");

        // 2. ADDING
        // User
        userService.add(user1);
        userService.add(user2);
        userService.add(user3);

        // 3. GETTING BY EMAIL
        // User
        System.out.println("\n *** CREATE AND READ FUNCTIONS TEST *** \n");
        System.out.println(userService.findByEmail("user1@gmail.com"));
        System.out.println(userService.findByEmail("user2@gmail.com"));
        System.out.println(userService.findByEmail("user3@gmail.com"));

        // 4. REGISTRATION
        // Correct
        authenticationService.registerUser("jan@gmail.com", "haslojana");
        System.out.println(userService.findByEmail("jan@gmail.com"));

        // 5. LOGIN
        System.out.println(authenticationService.login("jan@gmail.com", "haslojana"));
    }
}
