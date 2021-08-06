package mate.academy;

import mate.academy.exception.UserAuthenticationException;
import mate.academy.lib.Injector;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;

public class Main {
    public static void main(String[] args) throws UserAuthenticationException {
        Injector injector = Injector.getInstance("mate.academy");
        User user1 = new User();
        user1.setEmail("abc");
        user1.setPassword("qwerty");
        AuthenticationService authenticationService =
                (AuthenticationService) injector.getInstance(AuthenticationService.class);
        authenticationService.register("abc", "qwerty");
        User userbyLogin = authenticationService.login("abc", "qwerty");
        System.out.println(userbyLogin.toString());
    }
}
