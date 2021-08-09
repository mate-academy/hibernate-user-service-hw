package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.lib.Injector;
import mate.academy.model.User;
import mate.academy.security.AuthenticationService;
import mate.academy.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger logger = LogManager.getLogger();
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final AuthenticationService authenticationService =
            (AuthenticationService) injector.getInstance(AuthenticationService.class);
    private static final UserService userService = (UserService) injector.getInstance(UserService.class);

    public static void main(String[] args) {
        userService.add(new User("eugenesinica@gmail.com", "bigboy2012"));
        String eugenesinica = null;
        try {
            eugenesinica = "eugenesinica@gmail.com";
            authenticationService.register(eugenesinica, "bigboy2012");
        } catch (AuthenticationException e) {
            logger.error("AuthenticationService error while registering with email " + eugenesinica);
            logger.error(e.getMessage());
        }

        String dashakhmara = null;
        try {
            dashakhmara = "dashakhmara@gmail.com";
            authenticationService.register(dashakhmara, "littlegirl2012");
        } catch (AuthenticationException e) {
            logger.error("AuthenticationService error while registering with email " + dashakhmara);
            logger.error(e.getMessage());
        }

        String teabag = null;
        try {
            teabag = "teabag@gmail.com";
            authenticationService.register(teabag, "teabag1213");
        } catch (AuthenticationException e) {
            logger.error("AuthenticationService error while registering with email " + teabag);
            logger.error(e.getMessage());
        }


        try {
            authenticationService.login(eugenesinica, "bigboy2012");
        } catch (AuthenticationException e) {
            logger.error("AuthenticationService error while logging in with email " + eugenesinica);
            logger.error(e.getMessage());
        }

        try {
            authenticationService.login(dashakhmara, "littlegirl2012");
        } catch (AuthenticationException e) {
            logger.error("AuthenticationService error while logging in with email " + dashakhmara);
            logger.error(e.getMessage());
        }

        try {
            authenticationService.login(teabag, "WRONG TEABAG PASSWORD");
        } catch (AuthenticationException e) {
            logger.error("AuthenticationService error while logging in with email " + teabag);
            logger.error(e.getMessage());
        }
    }
}
