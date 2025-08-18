package mate.academy.service.impl;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AuthenticationServiceImplTest {
    private AuthenticationService authenticationService;
    private UserService userService;
    private static final Injector injector = Injector.getInstance("mate.academy");

    @Before
    public void setUp() {
        authenticationService = (AuthenticationService) injector.getInstance(AuthenticationService.class);
        userService = (UserService) injector.getInstance(UserService.class);
    }

    @Test
    public void login_wrongEmail_NotOk() throws Exception {
        String password = "1234";
        String email = "wrongEmail";

        Assert.assertThrows(AuthenticationException.class, () ->
                authenticationService.login(email, password));
    }

    @Test
    public void login_wrongPassword_NotOk() throws Exception {
        String password = "wrongPass";
        String email = "admin";

        Assert.assertThrows(AuthenticationException.class, () ->
                authenticationService.login(email, password));
    }

    @Test
    public void login_success() throws Exception {
        String password = "admin123";
        String email = "admin123";
        authenticationService.register(email, password);
        User actual = authenticationService.login(email, password);
        Assert.assertNotNull(actual);
    }

    @Test
    public void register_existUser_NotOk() throws Exception {
        String password = "admin";
        String email = "admin";

        Assert.assertThrows(RegistrationException.class, () ->
                authenticationService.register(email, password));
    }

    @Test
    public void register_emptyPassword_NotOk() throws Exception {
        String password = "";
        String email = "admin";

        Assert.assertThrows(RegistrationException.class, () ->
                authenticationService.register(email, password));
    }

    @Test
    public void register_success() throws Exception {
        String password = "admin";
        String email = "admin";

        authenticationService.register(email, password);
        Assert.assertNotNull(userService.findByEmail("admin"));
    }

}
