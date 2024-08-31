package mate.academy.util;

import com.password4j.BcryptFunction;
import com.password4j.Password;
import com.password4j.types.Bcrypt;

public class HashUtil {
    private static final BcryptFunction bcrypt = BcryptFunction
            .getInstance(Bcrypt.B, 12);

    private HashUtil() {

    }

    public static String hashPassword(String password) {
        return Password.hash(password)
                .with(bcrypt)
                .getResult();
    }
}
