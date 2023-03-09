package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.model.User;
import mate.academy.security.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

public class Main {
    public static void main(String[] args) throws AuthenticationException, RegistrationException {

        Injector injector = Injector.getInstance("mate.academy");

        User user = new User();
        user.setSalt(HashUtil.getSalt());
        user.setPassword(HashUtil.hashPassword("asdfgh", user.getSalt()));
        user.setEmail("dmytro@ukr.net");
        UserService userService = (UserService) injector.getInstance(UserService.class);
        System.out.println(userService.add(user));
        System.out.println(userService.findByEmail("yuiriy@ukr.net").get());

        AuthenticationService authenticationService
                = (AuthenticationService) injector.getInstance(AuthenticationService.class);
        System.out.println(authenticationService.login("bogdan@ukr.net", "12345678"));
        System.out.println(authenticationService.register("dmytro@ukr.net", "asdfgh"));
    }
}
