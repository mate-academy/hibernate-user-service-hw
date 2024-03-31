package mate.academy.service;

import javax.naming.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Service;
import mate.academy.model.User;

@Service
public interface AuthenticationService {
    User login(String email, String password) throws AuthenticationException;

    void register(String email, String password) throws RegistrationException;
}
