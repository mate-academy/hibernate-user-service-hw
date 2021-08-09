package mate.academy.util;

public class GenerateSalt {
    private static final byte MAX_NUMBER = 20;

    public byte[] generateSalt() {
        byte[] salt = {(byte) (Math.random() * MAX_NUMBER)};
        return salt;
    }
}
