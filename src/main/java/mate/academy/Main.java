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
        User user = authentificationService.login("ravshan567@gmail.com",
                "ravshan4ik4irik");
        System.out.println(user);
    }
}
