package mate.academy;

import mate.academy.lib.Injector;
import mate.academy.model.User;
import mate.academy.service.AuthentificationService;

public class Main {
    private static final Injector injector = Injector
            .getInstance("mate.academy");

    public static void main(String[] args) {
        AuthentificationService authentificationService = (AuthentificationService)
                injector.getInstance(AuthentificationService.class);
        authentificationService.register("babajka@gmail.com", "babajka123");
        User user = authentificationService.login("babajka@gmail.com", "babajka123");
        System.out.println(user);
    }
}
