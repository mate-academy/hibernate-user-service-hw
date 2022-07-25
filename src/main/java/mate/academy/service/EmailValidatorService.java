package mate.academy.service;

import mate.academy.exception.DataValidationException;

public interface EmailValidatorService {
    boolean isValid(String email) throws DataValidationException;
}
