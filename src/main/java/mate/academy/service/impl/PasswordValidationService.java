package mate.academy.service.impl;

import mate.academy.exception.RegistrationException;
import mate.academy.lib.Service;
import mate.academy.service.PasswordValidation;

@Service
public class PasswordValidationService implements PasswordValidation {
    public static final int PASSWORD_LENGTH = 8;
    public static final int ASCII_A = 65;
    public static final int ASCII_Z = 90;
    public static final int ASCII_ZERO = 48;
    public static final int ASCII_NINE = 57;

    public void passwordValidator(String password) throws RegistrationException {
        if (password.length() < PASSWORD_LENGTH
                            && containsUpperCase(password)
                            && containsDigits(password)) {
            throw new RegistrationException("You should use password longer than 8 symbols,"
                    + " it should contain at least one digit and one upper case letter!");
        }
    }

    private boolean containsUpperCase(String password) {
        String upperCaseLetters = password
                .chars()
                .filter(i -> i >= ASCII_A && i <= ASCII_Z)
                .toString();
        return upperCaseLetters.length() != 0;
    }

    private boolean containsDigits(String password) {
        String digits = password
                .chars()
                .filter(i -> i >= ASCII_ZERO && i <= ASCII_NINE)
                .toString();
        return digits.length() != 0;
    }
}
