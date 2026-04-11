package mate.academy.service;

public interface SecurityService {
    String hashPassword(String password, String salt);

    String generateSalt();
}