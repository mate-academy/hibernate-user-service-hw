package mate.academy.service;

public interface UserService {
    User add(User user);

    Optional<User> findByEmail(String email);
}
