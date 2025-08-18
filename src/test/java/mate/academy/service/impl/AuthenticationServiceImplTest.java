package mate.academy.service.impl;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Optional;

public class AuthenticationServiceImplTest {

    private AuthenticationService authenticationService;
    private UserService userService;

    @Before
    public void setUp() {
        userService = Mockito.mock(UserService.class);
        authenticationService = new AuthenticationServiceImpl(userService);
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
        String email = "admin123";
        String password = "admin123";
        byte[] salt = new byte[16];

        User user = new User();
        user.setEmail(email);
        user.setSalt(salt);
        user.setPassword(HashUtil.hashPassword(password, salt));

        Mockito.when(userService.findByEmail(email)).thenReturn(Optional.of(user));

        User actual = authenticationService.login(email, password);
        Assert.assertNotNull(actual);


    }

    @Test
    public void register_existUser_NotOk() throws Exception {
        User existingUser = new User();
        existingUser.setEmail("admin");

        Mockito.when(userService.findByEmail("admin")).thenReturn(Optional.of(existingUser));

        Assert.assertThrows(RegistrationException.class, () ->
                authenticationService.register("admin", "admin"));

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
