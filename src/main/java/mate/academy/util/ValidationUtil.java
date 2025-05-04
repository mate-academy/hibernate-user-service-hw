package mate.academy.util;

public class ValidationUtil {
    private static final String REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";

    public static boolean validateEmail(String email) {
        return email != null && email.matches(REGEX);
    }
}
