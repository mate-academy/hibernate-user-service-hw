package mate.academy;

import mate.academy.lib.Injector;
import mate.academy.model.User;
import mate.academy.security.AuthentificationService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static AuthentificationService authentificationService;
    private static final int EMAIL_INDEX = 0;
    private static final int PASSWORD_INDEX = 1;
    private static final String[] USER_1 = {"u1@gmail.com", "qwerty"};
    private static final String[] USER_2 = {"u2@gmail.com", "qwerty"};
    private static final String[] USER_3 = {"u3@gmail.com", "asdfg"};

    public static void main(String[] args) {
        authentificationService = (AuthentificationService) injector
                .getInstance(AuthentificationService.class);

        initUsers();
        try {
            User loginnedUser = authentificationService
                    .login(USER_1[EMAIL_INDEX], USER_1[PASSWORD_INDEX]);
            System.out.println("User successfully authorized: " + loginnedUser);
        } catch (Exception e) {
            System.out.println("User not authorized: " + e);
        }
    }

    private static void initUsers() {
        try {
            authentificationService.register(USER_1[EMAIL_INDEX], USER_1[PASSWORD_INDEX]);
            authentificationService.register(USER_2[EMAIL_INDEX], USER_2[PASSWORD_INDEX]);
            authentificationService.register(USER_3[EMAIL_INDEX], USER_3[PASSWORD_INDEX]);
            authentificationService.register(USER_3[EMAIL_INDEX], USER_3[PASSWORD_INDEX]);
        } catch (Exception e) {
            System.out.println("Can't add user " + e);;
        }
    }
}
