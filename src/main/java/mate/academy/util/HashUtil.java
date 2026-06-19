package mate.academy.util;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class HashUtil {
    private static final Argon2 argon2 = Argon2Factory.create();

    public HashUtil() {
    }

    public static String hashPassword(String password) {
        return argon2.hash(10, 65536, 1, password.toCharArray());
    }

    public static boolean verifyPassword(String password, String hashedPassword) {
        try {
            return argon2.verify(hashedPassword, password.toCharArray());
        } finally {
            argon2.wipeArray(password.toCharArray());
        }
    }
}
