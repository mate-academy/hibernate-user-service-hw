package mate.academy.util;

import java.util.regex.Pattern;

public class Validator {
    private static final String EMAIL_REGEXP = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    private static final String PASSWORD_REGEXP
            = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";

    public static boolean validateEmail(String email) {
        return Pattern.compile(EMAIL_REGEXP)
                .matcher(email)
                .matches();
    }

    public static boolean validatePassword(String password) {
        return Pattern.compile(PASSWORD_REGEXP)
                .matcher(password)
                .matches();
    }
}
