package mate.academy.service;

import mate.academy.exception.RegistrationException;

public interface PasswordValidation {
    void passwordValidator(String password) throws RegistrationException;
}
