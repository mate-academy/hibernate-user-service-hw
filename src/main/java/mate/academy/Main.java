package mate.academy;

import mate.academy.lib.Injector;
import mate.academy.service.AuthenticationService;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");
        AuthenticationService auth =
                (AuthenticationService) injector.getInstance(AuthenticationService.class);

        try {
            var u = auth.register("john@doe.com", "secret");
            System.out.println("Registered: " + u);
            var logged = auth.login("john@doe.com", "secret");
            System.out.println("Logged in: " + logged);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
