package mate.academy.service;

import mate.academy.exception.RegistrationException;

public interface EmailValidatorService {
    boolean isValid(String email) throws RegistrationException;
}
