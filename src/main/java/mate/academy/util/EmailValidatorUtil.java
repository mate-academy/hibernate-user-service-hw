package mate.academy.util;

import java.util.List;

public class EmailValidatorUtil {
    private static final String ONLY_NUMBERS_OR_WORDS = "^[A-Za-z0-9]+$";
    private static final String EMAIL_AT_SIGN = "@";
    private static final int DOMEN = 1;
    private static final int USER_NAME = 0;
    private static final List<String> supportedDomens =
            List.of("gmail.com", "yahoo.com", "outlook.com", "hotmail.com");

    public static boolean isValidEmail(String email) {
        String[] split = email.split(EMAIL_AT_SIGN);
        if (!(split.length == 2 && email.contains(EMAIL_AT_SIGN))) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (!supportedDomens.contains(split[DOMEN])) {
            throw new IllegalArgumentException(split[DOMEN]
                    + " is not supported. "
                    + "supported domens: " + supportedDomens);
        }
        if (!(split[USER_NAME].matches(ONLY_NUMBERS_OR_WORDS))) {
            throw new IllegalArgumentException("email must contain only numbers or words");
        }
        return true;
    }
}
