package mate.academy.service;

import mate.academy.exception.RegistrationValidationException;

public interface ValidationService {
    boolean isValid(String email, String password) throws RegistrationValidationException;
}
