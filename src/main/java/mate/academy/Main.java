package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;

public class Main {
    public static void main(String[] args) throws AuthenticationException, RegistrationException {
        final Injector injector
                = Injector.getInstance("mate.academy");
        AuthenticationService authenticationService
                = (AuthenticationService) injector.getInstance(AuthenticationService.class);
        User loginUser = null;
        loginUser = authenticationService.login("test@test.com", "TESTtest!2");
        System.out.println(loginUser);
        User registerUser = null;
        registerUser = authenticationService.register("new@new.com", "PaSSWord!231!");
        System.out.println(registerUser);
    }
}
