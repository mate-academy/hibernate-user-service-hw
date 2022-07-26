package mate.academy.service.impl;

import java.util.regex.Pattern;
import mate.academy.exception.RegistrationValidationException;
import mate.academy.lib.Service;
import mate.academy.service.ValidationService;

@Service
public class ValidationServiceImpl implements ValidationService {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("\\w+@\\w+\\.\\w+");
    private static final int PASSWORD_MIN_LENGTH = 8;

    @Override
    public boolean isValid(String email, String password)
            throws RegistrationValidationException {
        if (!EMAIL_PATTERN.matcher(email).matches()
                || password.length() < PASSWORD_MIN_LENGTH) {
            throw new RegistrationValidationException("Login or password is incorrect.");
        }
        return true;
    }
}
